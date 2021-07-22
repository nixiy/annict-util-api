# SpringTest
Spring 知見リポジトリ

## Validate
- ControllerのRequestParamに直接バリデーションを記述した場合にはExceptionが発生するのでExceptionHandlerで補足する
- ModelAttributeで別ファイルとしてリクエストクエリパラメータを受けた際は、直後の引数のBindingResultにエラー内容等が入る
    - バリデーションに引っかかってもExceptionは発生しないので、適宜hasErrors()等で確認する
