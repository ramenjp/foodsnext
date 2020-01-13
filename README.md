# プロダクト名：Food's Next
だれかと食事をとりたくても気軽にご飯に誘える人がいない社員、また社内交流を図りたい社員をターゲットに、ランチを社内の誰かと気軽に取れるマッチングアプリを作成しました。
プロダクト名は"食"とWho's Nextをかけました。

# はじめに
このプロダクト研修の一環として行われたハッカソンにおいて、研修教材として渡された部分のファイルをアップせず、私が新たに作成したサーバー部分のみをアップしております。
よってファイルが不十分なためクローンしてもアプリとして成り立ってはいませんがロジックやイメージ図をアップロードしておりますのでご覧ください。

# アーキテクチャ
entity

DBのカラムとJava側の変数とを紐づけるファイル

repository

DBを操作するクエリを格納するファイル

service

controllerから指示を受けてDB操作等を行う

controller
URLパターンに応じてserviceやrepositoryに指示を出し、対応するHTMLファイルを画面に表示させる

# 機能一覧
- ログイン
- 新規会員登録
- ランダムマッチング
- マッチング履歴
- プロフィール更新

# 使用技術
- Java
- Springboot
- Thymeleaf
- バッチ処理
- carl

# DB
![プレゼンテーション1](https://user-images.githubusercontent.com/49260657/72248455-8ecca200-363a-11ea-8170-9e84a90125fe.jpg)

# マッチング機能ロジック
![Food'sNextロジック](https://user-images.githubusercontent.com/49260657/72262923-38bc2680-365b-11ea-8b33-73e1b73f32a0.jpg)

# 画面
