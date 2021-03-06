# Annict APIをラッピングしたAPIを作ってみる

[![CodeQL](https://github.com/nixiy/annict-util-api/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/nixiy/annict-util-api/actions/workflows/codeql-analysis.yml)

## 目的
- Spring bootの練習
- GraphQLに慣れる
  - [x] Apollo Clientに食わせるQueryを書けるようになる
  - [x] Apollo Clientのレスポンスを同期的に待つ
- Annictにおいて、以下のような事がやりたくなったためApiを組み合わせてどうにかできないか施行
  - [x] 作品ステータスが同一 or 異なる
    - お互い視聴完了した作品って何なんだっけという場面が多々ある
  - [x] 相手ユーザのみステータスが存在する作品の発掘
  - [ ] 視聴傾向が似ているユーザの発掘
    - 似ているとは何なのかの定義
    - 視聴記録が一致している作品数 / 視聴記録数 ?
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

### リクエストのタイムアウト
OkHttpのデフォルト値では、`connectTimeout`, `readTimeout`, `writeTimeout` がそれぞれ10000(ms) で設定されているようである。

※ [OkHttpClient.Builder][okhttp]のページで`10 seconds.`で検索をかけてもらうとヒットする

ステータスが大量にあるユーザだと、上記の秒数に収まらないため、一旦タイムアウト値を20000(ms)としている
- そもそもこれは、一度に取得する件数の上限を設けるなどの対応が妥当かもしれない

[comment]: <> (リンク類)
[Altair]: https://github.com/altair-graphql/altair
[Token]: https://developers.annict.jp/graphql-api/personal-access-token/#%E3%83%88%E3%83%BC%E3%82%AF%E3%83%B3%E3%82%92%E7%94%9F%E6%88%90%E3%81%99%E3%82%8B
[okhttp]: https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.Builder.html
