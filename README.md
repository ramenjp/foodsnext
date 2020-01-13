# プロダクト名：foodsnext

# はじめに
このプロダクトは研修教材をベースに書き加えているため会社から提供されたファイルをアップしておりません。
よってクローンしてもシステムは動かせませんがREADMEを代わりとさせていただきます。

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

# ロジック

# 画面
