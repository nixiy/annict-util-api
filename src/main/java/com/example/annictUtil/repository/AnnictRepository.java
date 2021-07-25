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

import java.util.concurrent.TimeUnit;

@Repository
public class AnnictRepository {
    @Autowired
    AnnictProperties annictProperties;

    /**
     * 指定した状態の作品一覧を取得する
     * FIXME: ユーザによっては履歴が大量にあり、タイムアウトしてしまう事があるため、OkHttpでのタイムアウト値を検討してみるべき
     * @param username
     * @param statusState 視聴状態を指定して絞り込み (nullで全状態で検索)
     * @return
     */
    public UserQuery.User getUser(String username, StatusState statusState) {
        System.out.println("annictProperties.getReadTimeoutMs() = " + annictProperties.getReadTimeoutMs());
        // 認可用ヘッダの追加
        final var okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(annictProperties.getReadTimeoutMs(), TimeUnit.MILLISECONDS)
                .authenticator((route, response) -> response.request()
                        .newBuilder()
                        .addHeader("Authorization", "bearer " + annictProperties.getAccessToken()) //Annict AccessTokenでの認可
                        .build()
                ).build();

        // Apollo Clientのセットアップ
        final var apolloClient = ApolloClient.builder()
                .okHttpClient(okHttpClient)
                .serverUrl(annictProperties.getGraphqlEndpoint())
                .build();

        // Annict GraphQL Queryの設定(引数含む)
        final var query = UserQuery.builder()
                .username(username)
                .state(statusState)
                .build();

        final var apolloQueryCall = apolloClient.query(query);

        return Rx3Apollo.from(apolloQueryCall)
                .map(Response::getData)
                .map(UserQuery.Data::user)
                .blockingFirst(); //Rx3Apolloを用いた非同期→同期待機の実現
    }
}
