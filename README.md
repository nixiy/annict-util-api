# Annict APIをラッピングしたAPIを作ってみる

## 目的
- Spring bootの練習
- GraphQLに慣れる
  - Apollo Clientも合わせて
- Annictにおいて、以下のような事がやりたくなったためApiを組み合わせてどうにかできないか施行
  - 見終わった作品のAND, OR
    - で、お互い視聴完了した作品って何なんだっけという場面が多々ある
  - 相手ユーザのみステータスが存在する作品の発掘
  - 視聴傾向が似ているユーザの発掘
  - etc...
  

## 開発メモ
### 前提
- AnnictAPIへのリクエストは、[アクセストークン][Token]が必要になる
- リクエスト時には、以下の`${TOKEN}`を埋めてヘッダに付与する必要がある。

```bash
Authorization: Bearer ${TOKEN}
```

- 本プロジェクトにおいては、アクセストークンを`application.properties`の`annict.accessToken`に記述する事で実行時にヘッダにセットされる。

### *.graphql
Apolloにおいて、取得したいQueryやMutationを記述してビルドすると、記述内容に応じてJava用の型定義等を自動生成してくれる。

[Altair GraphQL Client][Altair] というClientを用いて取得したいクエリを作成した。

- Dockから追加したいQueryやMutationにカーソルを合わせると追加を促すようなボタンが出てくる
- クリックするとQueryが生成される 
  - 変数等は `'______'` になっているため、必要に応じて埋める
  - または引数がNullableであれば引数から落とす


### schema.json
graphqlのエンドポイントから取得できる、全体のスキーマ構造。

Apollo Client においては作成したgraphqlファイルからクラスを自動生成するために必要になる。

コマンドプロンプトとbash on ubuntu on windowsではうまくいなかった

※ build.gradleにdownloadApolloSchemaを宣言していたので、そちらの設定が優先されてしまったのかもしれない → 削除後に成功

　→ GitBashのbash上で実行で成功

```bash
./gradlew downloadApolloSchema \ 
  --endpoint="https://api.annict.com/graphql" \ 
  --schema="src/main/graphql/annict/schema.json"  
  --header="Authorization: Bearer ${TOKEN}"
```

[comment]: <> (リンク類)
[Altair]: https://github.com/altair-graphql/altair
[Token]: https://developers.annict.jp/graphql-api/personal-access-token/#%E3%83%88%E3%83%BC%E3%82%AF%E3%83%B3%E3%82%92%E7%94%9F%E6%88%90%E3%81%99%E3%82%8B
