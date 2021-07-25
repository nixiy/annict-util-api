package com.example.annictUtil.repository;

import annict.UserQuery;
import annict.type.StatusState;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx3.Rx3Apollo;
import com.example.annictUtil.properties.AnnictProperties;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnnictRepository {
    @Autowired
    AnnictProperties annictProperties;

    public UserQuery.User getUser(String username) {
        // 認可用ヘッダの追加
        final var okHttpClient = new OkHttpClient().newBuilder().authenticator((route, response) -> response.request().newBuilder()
                .addHeader("Authorization", "bearer " + annictProperties.getAccessToken()) //Annict AccessTokenでの認可
                .build()).build();

        // Apolli Clientのセットアップ
        final var apolloClient = ApolloClient.builder()
                .okHttpClient(okHttpClient)
                .serverUrl(annictProperties.getGraphqlEndpoint())
                .build();

        // Annict GraphQL Queryの設定(引数含む)
        final var query = UserQuery.builder()
                .username(username)
                .state(StatusState.WATCHING)
                .build();

        final var apolloQueryCall = apolloClient.query(query);

        return Rx3Apollo.from(apolloQueryCall)
                .map(Response::getData)
                .map(UserQuery.Data::user)
                .blockingFirst(); //Rx3Apolloを用いた非同期→同期待機の実現
    }
}
