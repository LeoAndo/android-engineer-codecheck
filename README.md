# アプリ仕様

Githubのリポジトリを検索するアプリになります。<br>

# 開発環境
- IDE: Android Studio Dolphin 2021.3.1 Patch 1
- 動作OSバージョン: OS 6.0以上

# アプリ開発時の準備 (重要)
秘匿情報の管理を`local.properties`で行っています。<br>
[fine-grained personal access token作成手順](https://docs.github.com/ja/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#fine-grained-personal-access-token-%E3%81%AE%E4%BD%9C%E6%88%90) に沿ってaccess tokenを作成し、以下の11行目の通り設定値を追加してください。
<img src= "https://user-images.githubusercontent.com/16476224/208823985-4d0fa8ec-ef81-48de-94e7-8552728e304a.png" />

# デザイン面：アピールポイント
- Themed Iconの適用
- Material3 Themeの適用
- 従来のAndroid Viewシステムを使用
- Dark Theme対応

# 動作確認

<strong> 以降の動作確認では、暗号化されたリリースビルドで動作確認を行なっています。 </strong>

## サポートOSの動作確認
| Resizable Emulator API 33 | Pixel6 API 23 |
|:---|:---:|
|<img src="https://user-images.githubusercontent.com/16476224/210133013-e7fbc548-3df9-4b86-997c-4b04389d99fd.gif" width=320 /> | <img src="https://user-images.githubusercontent.com/16476224/210133039-c65133e7-4ef9-4fe4-a356-906c9202ef2a.gif" width=320 /> |

## Themed Iconの適用
| Resizable Emulator API 33 |
|:---|
|<img src="https://user-images.githubusercontent.com/16476224/210133068-33cb9e8d-5b57-40b8-ab9a-c918415ad5fe.png" width=320 />

## Dark Themeの確認
| Resizable Emulator API 33 |
|:---|
|<img src="https://user-images.githubusercontent.com/16476224/210132958-68a07cd5-38b2-42c3-aeef-f02e58fb772c.gif" width=320 />


## 異常系: Resizable Emulator API 33
| 接続エラー | 401: 認証エラー | 403: アクセス回数制限 |
|:---|:---|:---|
|<img src="https://user-images.githubusercontent.com/16476224/210133261-4138e04d-1132-4cdd-8450-f4215ba55c50.gif" width=320 />|<img src="https://user-images.githubusercontent.com/16476224/210133495-3242e004-dee6-4d47-844c-ef3db9cd1b11.png" width=320 />|<img src="https://user-images.githubusercontent.com/16476224/210133567-5746f6b7-a385-41f8-92b7-4e70a6107c58.png" width=320 />|
