CREATE DATABASE  IF NOT EXISTS `healsweetsdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `healsweetsdb`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: healsweetsdb
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `allergy_setting`
--

DROP TABLE IF EXISTS `allergy_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allergy_setting` (
  `setting_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `member_id` bigint NOT NULL COMMENT 'FK: MEMBERテーブルを参照',
  `allergen_code` varchar(10) NOT NULL COMMENT 'FK: アレルゲンマスタを参照（例: EGG, MILK, WHEAT）',
  `importance_level` int DEFAULT NULL COMMENT '1:軽度, 2:重度など',
  PRIMARY KEY (`setting_id`),
  UNIQUE KEY `UQ_MEMBER_ALLERGEN` (`member_id`,`allergen_code`),
  KEY `allergen_code` (`allergen_code`),
  CONSTRAINT `allergy_setting_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `allergy_setting_ibfk_2` FOREIGN KEY (`allergen_code`) REFERENCES `m_allergen` (`allergen_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='アレルギー設定';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergy_setting`
--

LOCK TABLES `allergy_setting` WRITE;
/*!40000 ALTER TABLE `allergy_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `allergy_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `family_birthday`
--

DROP TABLE IF EXISTS `family_birthday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `family_birthday` (
  `birthday_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `member_id` bigint NOT NULL COMMENT 'FK: MEMBERテーブルを参照',
  `person_name` varchar(100) NOT NULL COMMENT '家族・友人の氏名/ニックネーム',
  `birth_date` date NOT NULL,
  `relationship` varchar(50) DEFAULT NULL COMMENT '例: 夫、娘、友人',
  PRIMARY KEY (`birthday_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `family_birthday_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='家族・友人の誕生日';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `family_birthday`
--

LOCK TABLES `family_birthday` WRITE;
/*!40000 ALTER TABLE `family_birthday` DISABLE KEYS */;
/*!40000 ALTER TABLE `family_birthday` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `favorite_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `member_id` bigint NOT NULL COMMENT 'FK: MEMBERテーブルを参照',
  `product_id` bigint NOT NULL COMMENT 'FK: PRODUCTテーブルを参照',
  `added_at` datetime NOT NULL,
  PRIMARY KEY (`favorite_id`),
  UNIQUE KEY `UQ_MEMBER_PROD` (`member_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='お気に入り商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gift_option`
--

DROP TABLE IF EXISTS `gift_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gift_option` (
  `option_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `option_name` varchar(100) NOT NULL COMMENT '例: 標準ラッピング、リボン、メッセージカード',
  `option_price` int NOT NULL COMMENT 'オプションの価格',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '現在提供中のオプションか',
  PRIMARY KEY (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ギフトオプション';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gift_option`
--

LOCK TABLES `gift_option` WRITE;
/*!40000 ALTER TABLE `gift_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `gift_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_admin_user`
--

DROP TABLE IF EXISTS `m_admin_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_admin_user` (
  `ADMIN_ID` int NOT NULL AUTO_INCREMENT COMMENT '管理者を一意に識別',
  `LOGIN_ID` varchar(50) NOT NULL COMMENT 'ログインに使用するID (ユニーク)',
  `PASSWORD_HASH` varchar(255) NOT NULL COMMENT 'パスワードのハッシュ値',
  `ADMIN_NAME` varchar(100) NOT NULL COMMENT '管理者氏名',
  PRIMARY KEY (`ADMIN_ID`),
  UNIQUE KEY `LOGIN_ID` (`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理者マスタ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_admin_user`
--

LOCK TABLES `m_admin_user` WRITE;
/*!40000 ALTER TABLE `m_admin_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_admin_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_allergen`
--

DROP TABLE IF EXISTS `m_allergen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_allergen` (
  `allergen_code` varchar(10) NOT NULL COMMENT '主キー（EGG, MILK, WHEATなど）',
  `allergen_name` varchar(50) NOT NULL COMMENT '日本語名（例: 卵、乳、小麦）',
  PRIMARY KEY (`allergen_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='アレルゲンマスター';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_allergen`
--

LOCK TABLES `m_allergen` WRITE;
/*!40000 ALTER TABLE `m_allergen` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_allergen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_category`
--

DROP TABLE IF EXISTS `m_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_category` (
  `cat_id` int NOT NULL,
  `cat_name` varchar(50) NOT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_category`
--

LOCK TABLES `m_category` WRITE;
/*!40000 ALTER TABLE `m_category` DISABLE KEYS */;
INSERT INTO `m_category` VALUES (1,'アレルゲンフリー'),(2,'低糖質'),(3,'カカオ70%');
/*!40000 ALTER TABLE `m_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_delivery_time_slot`
--

DROP TABLE IF EXISTS `m_delivery_time_slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_delivery_time_slot` (
  `TIME_SLOT_CODE` int NOT NULL AUTO_INCREMENT COMMENT '配送希望時間帯を一意に識別',
  `DISPLAY_NAME` varchar(50) NOT NULL COMMENT '例: 「午前中」「14時～16時」',
  `START_TIME` time DEFAULT NULL COMMENT '内部処理用（配送可否判定など）',
  `END_TIME` time DEFAULT NULL COMMENT '内部処理用（配送可否判定など）',
  PRIMARY KEY (`TIME_SLOT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配送時間帯マスター';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_delivery_time_slot`
--

LOCK TABLES `m_delivery_time_slot` WRITE;
/*!40000 ALTER TABLE `m_delivery_time_slot` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_delivery_time_slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_shipping_fee_rule`
--

DROP TABLE IF EXISTS `m_shipping_fee_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_shipping_fee_rule` (
  `RULE_ID` int NOT NULL AUTO_INCREMENT COMMENT '配送料金ルールを一意に識別',
  `AREA_CODE` varchar(10) NOT NULL COMMENT 'FK: 地域マスタ（未定義の場合、別途必要）。',
  `TYPE_CODE` int NOT NULL COMMENT 'FK: M_SHIPPING_TYPE。',
  `FEE_AMOUNT` decimal(10,2) NOT NULL COMMENT 'この地域・形態での基本料金',
  `START_DATE` date NOT NULL COMMENT '料金改定の履歴管理用（最新の料金特定に利用）',
  PRIMARY KEY (`RULE_ID`),
  KEY `TYPE_CODE` (`TYPE_CODE`),
  CONSTRAINT `m_shipping_fee_rule_ibfk_1` FOREIGN KEY (`TYPE_CODE`) REFERENCES `m_shipping_type` (`TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配送料金ルール';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_shipping_fee_rule`
--

LOCK TABLES `m_shipping_fee_rule` WRITE;
/*!40000 ALTER TABLE `m_shipping_fee_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_shipping_fee_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_shipping_type`
--

DROP TABLE IF EXISTS `m_shipping_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_shipping_type` (
  `TYPE_CODE` int NOT NULL AUTO_INCREMENT COMMENT '運送形態を一意に識別',
  `TYPE_NAME` varchar(50) NOT NULL COMMENT '例: 「常温便」「クール冷蔵便」「冷凍便」',
  `FEE_FLG` int NOT NULL COMMENT '1: 通常料金と別枠で追加料金が発生する形態',
  PRIMARY KEY (`TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='運送形態マスター';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_shipping_type`
--

LOCK TABLES `m_shipping_type` WRITE;
/*!40000 ALTER TABLE `m_shipping_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_shipping_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_system_config`
--

DROP TABLE IF EXISTS `m_system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `m_system_config` (
  `CONFIG_KEY` varchar(50) NOT NULL COMMENT '設定を一意に識別 (例: TAX_RATE, FREE_SHIP_THRESHOLD)',
  `CONFIG_VALUE` varchar(255) NOT NULL COMMENT '設定値 (例: 10, 5000)',
  `CONFIG_NAME` varchar(100) NOT NULL COMMENT '管理画面表示用名称',
  `UPD_DATE` datetime NOT NULL COMMENT '最終更新日時',
  PRIMARY KEY (`CONFIG_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='システム設定';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_system_config`
--

LOCK TABLES `m_system_config` WRITE;
/*!40000 ALTER TABLE `m_system_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `member_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `email` varchar(255) NOT NULL COMMENT 'ログインIDとして使用（一意であること）',
  `password_hash` varchar(255) NOT NULL COMMENT 'パスワードのハッシュ値（Bcryptなど）',
  `sns_type` varchar(50) DEFAULT NULL COMMENT '連携SNS名（''LINE'', ''Google''など）',
  `sns_user_id` varchar(255) DEFAULT NULL COMMENT 'SNS連携時の一意なID',
  `last_login_at` datetime DEFAULT NULL COMMENT 'ログイン成功時に更新',
  `reset_token` varchar(64) DEFAULT NULL COMMENT 'パスワード再設定用の一時トークン',
  `token_expires_at` datetime DEFAULT NULL COMMENT 'パスワードリセットトークンの期限',
  `full_name` varchar(100) NOT NULL COMMENT '会員の氏名',
  `dm_reg_complete` tinyint(1) NOT NULL DEFAULT '1' COMMENT '登録完了DM通知の可否 (T/F)',
  `dm_limited_item` tinyint(1) NOT NULL DEFAULT '0' COMMENT '限定品DM通知の可否 (T/F)',
  `dm_campaign` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'キャンペーンDM通知の可否 (T/F)',
  `coupon_used_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '初回特典クーポンの利用有無 (T/F)',
  `postal_code` varchar(8) DEFAULT NULL,
  `prefecture` varchar(10) DEFAULT NULL,
  `address_line1` varchar(100) DEFAULT NULL,
  `address_line2` varchar(100) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `registration_date` datetime NOT NULL COMMENT '会員登録日時',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:有効, 0:退会など',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会員情報';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `product_name` varchar(255) NOT NULL,
  `PROD_DESC` text COMMENT '詳細説明、キーワード検索の対象',
  `description` text,
  `ingredient_detail` text COMMENT '原材料や栄養成分の詳細情報',
  `brand_story` text COMMENT '商品の背景や素材の特徴',
  `price` int NOT NULL COMMENT '価格（税込）',
  `MEMBER_PRICE` decimal(10,2) DEFAULT NULL COMMENT '会員/一般の価格切り替え用',
  `LIMITED_FLG` int NOT NULL COMMENT '限定品フィルター',
  `USAGE` varchar(255) NOT NULL COMMENT '用途別検索',
  `SEASON` int NOT NULL COMMENT 'シーズン検索',
  `stock_quantity` int NOT NULL COMMENT '在庫数',
  `expiration_storage` varchar(500) DEFAULT NULL COMMENT '消費期限と保存方法の説明',
  `is_gift_available` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ギフトラッピングの可否 (T/F)',
  `sale_status` varchar(20) NOT NULL COMMENT 'SALE, SOLD_OUTなど',
  `is_limited` tinyint(1) NOT NULL DEFAULT '0' COMMENT '限定商品か (T/F)',
  `is_low_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '残りわずかフラグ',
  `cat_id` bigint DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL COMMENT '商品画像URL',
  `unboxing_video_url` varchar(500) DEFAULT NULL COMMENT '開封動画URL',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品情報';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'雪解け メロンパン','「えっ、こんなに食べていいの？」と思わず疑ってしまうほど、真っ白な甘い粉糖が山盛りに降り積もったインパクト抜群のメロンパン。 見た目の背徳感とは裏腹に、ベースは国産米粉100%のグルテンフリー生地。外側はカリッと、中は豆乳クリームを練り込み、しっとりモチモチの食感に仕上げました。 お砂糖のように見える白い粉は、カロリーゼロの天然甘味料（エリスリトール等）を微粒子化した特製パウダー。口に入れた瞬間、柔らかな甘みが広がり、スッと溶けていきます。',NULL,'小麦粉・卵・乳製品不使用',NULL,520,NULL,0,'未設定',0,0,'製造から３日以内',0,'NONE',0,0,1,'/images/melonpan.png',NULL),(2,'完熟りんごのヘルシーパイ','「本当に小麦もバターも使っていないの？」と驚くほど、サクサクの食感にこだわったグルテンフリーのパイ生地が自慢の一品です。 中には、甘みと酸味のバランスが抜群なブランドりんごを、皮ごと贅沢に使用。甜菜糖とシナモンでじっくり煮込み、りんご本来の旨味を凝縮させました。 格子状の美しいパイ生地の上には、仕上げに希少糖のシロップを塗り、宝石のようなツヤと上品な甘さをプラス。温めると、中から熱々の蜜があふれ出します。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,2800,NULL,0,'未設定',0,0,'冷蔵：製造日を含め3日間 冷凍：約2週間（解凍後は当日中にお召し上がりください）',0,'NONE',0,0,1,'/images/applepie.png',NULL),(3,'まるごと桃の米粉タルト','旬の完熟桃をまるごと一個、贅沢にタルトの上に乗せました。 桃の中には、バニラビーンズ香るなめらかな豆乳カスタードをたっぷり詰め込んでいます。土台となるタルト生地は、国産米粉とアーモンドプードルで焼き上げた、サクサクと香ばしいグルテンフリー仕様。 桃のジューシーな甘さと、タルトの心地よい食感、そしてとろけるクリームの三位一体が楽しめる、季節限定の看板商品です。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,920,NULL,0,'未設定',0,0,'冷蔵：当日中',0,'NONE',0,0,2,'/images/peachtart.png',NULL),(4,'濃密カカオの米粉エクレア','ひと口食べれば、カカオの芳醇な香りが口いっぱいに広がる贅沢なエクレアです。 国産米粉を使用したシュー生地は、小麦粉不使用とは思えないほど香ばしく、歯切れの良い食感。中には、カカオ分85%のオーガニックチョコと豆乳を合わせた、なめらかな自家製チョコクリームを隙間なく詰め込みました。 表面にはビターチョコをコーティングし、仕上げに食感のアクセントとしてカカオニブをトッピング。甘さ控えめで、大人のためのヘルシースイーツに仕上げています。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,580,NULL,0,'未設定',0,0,'冷蔵：当日中',0,'NONE',0,0,3,'/images/cacaoeclair.png',NULL),(5,'無卵（ムーラン）・ルージュ','名前の通り、卵を一切使用せずに作り上げた、真っ赤な情熱の色が美しい苺のグラスデザートです。 下層には、完熟苺を惜しみなく使った濃厚な自家製コンフィチュールと、酸味の効いた苺ムースを重ねました。その上には、雪のような口溶けの豆乳ホイップクリームをたっぷりと。 トップを飾るフレッシュな苺の甘酸っぱさと、豆乳クリームの優しいコクが絶妙なハーモニーを奏でます。見た目は華やかですが、後味はスッキリと軽い、まさに「罪悪感ゼロ」のルージュです。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,680,NULL,0,'未設定',0,0,'冷蔵：当日中',0,'NONE',0,0,1,'/images/eggfreerouge.png',NULL),(6,'米粉のしっとり焼きドーナツ','「油で揚げない」から、とってもヘルシー。国産米粉と豆乳をベースに、じっくりと焼き上げた体に優しいドーナツです。 米粉ならではの**「しっとり・もちっ」**とした食感が特徴で、時間が経ってもパサつきません。 定番の「プレーン」、高カカオチョコを使用した「ショコラ」、香り高い「宇治抹茶」など、素材の味を大切に焼き上げました。朝食やお子様のおやつにも安心して選んでいただける、当店のロングセラー商品です。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,1450,NULL,0,'未設定',0,0,'常温保存：製造日より5日間',0,'NONE',0,0,1,'/images/bakeddonuts.png',NULL),(7,'さわやかレモンのしっとりおからケーキ','生おからをたっぷりと練り込んだ、驚くほどしっとり、それでいて口当たりの軽いヘルシーなケーキです。 生地には瀬戸内産のレモン果汁とゼスト（皮）を贅沢に使用し、一口ごとにレモンの爽やかな香りが弾けます。上には豆乳ベースの甘酸っぱいアイシングをたっぷりとかけ、仕上げにシロップ漬けのレモンとピスタチオをトッピング。 「おから感」を全く感じさせない、ティータイムにぴったりの上品な味わいに仕上げました。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,480,NULL,0,'未設定',0,0,'冷蔵：4日間',0,'NONE',0,0,1,'/images/lemoncake.png',NULL),(8,'米粉のココナッツマカロン','ココナッツを贅沢に使い、一口サイズに焼き上げた素朴でリッチな味わいのマカロンです（※メレンゲを使うフランス菓子ではなく、ココナッツを固めて焼いた伝統的なスタイルです）。 国産米粉と豆乳を使い、卵白を使わずに独自の製法でまとめ上げました。口に入れた瞬間にココナッツの南国風の香りが広がり、噛むほどに優しい甘みが溢れ出します。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,450,NULL,0,'未設定',0,0,'常温保存：製造日より14日間',0,'NONE',0,0,1,'/images/coconutmacaroons.png',NULL),(9,'米粉のクッキー詰め合わせ缶','「Healsweets」で人気の焼き菓子を、シックなネイビーのオリジナル缶に詰め合わせました。 定番のチョコチップクッキーから、甘酸っぱい自家製ジャムサンド、見た目も華やかなアイシングクッキーまで、すべてグルテンフリー＆ヴィーガン仕様。 一口ごとに異なる食感と味わいが楽しめ、最後まで飽きることなくお召し上がりいただけます。大切なティータイムを彩る、心にも体にも優しい詰め合わせです。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,3200,NULL,0,'未設定',0,0,'常温保存：製造日より30日間',0,'NONE',0,0,2,'/images/animalcookie.png',NULL),(10,'米粉のベリーチョコケーキ','カカオ分80%の濃厚なオーガニックチョコレートを贅沢に使用した、ずっしりと食べ応えのある本格派チョコケーキです。 小麦粉の代わりに国産米粉を使用し、卵や乳製品を使わずにしっとりと焼き上げました。層の間にはなめらかなチョコクリームを挟み、表面は艶やかなチョコグラサージュでコーティング。トップに飾ったラズベリーとブラックベリーの甘酸っぱさが、カカオの深いコクを引き立てます。特別な日のメインを飾るのにふさわしい、贅沢な一品です。',NULL,'不使用： 小麦、卵、乳、そば、落花生、えび、かに',NULL,4200,NULL,0,'未設定',0,0,'冷蔵：製造日を含め3日間',0,'NONE',0,0,3,'/images/chocolatecake.png',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_allergen`
--

DROP TABLE IF EXISTS `product_allergen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_allergen` (
  `association_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `product_id` bigint NOT NULL COMMENT 'FK: PRODUCTテーブルを参照',
  `allergen_code` varchar(10) NOT NULL COMMENT 'FK: アレルゲンマスタを参照（例: EGG, MILK, WHEAT）',
  `is_contained` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1: 含む (必須), 0: 含まない (アレルギー対応アイコンの表示に利用)',
  `note` varchar(255) DEFAULT NULL COMMENT '「製造ラインで混入の可能性あり」など',
  PRIMARY KEY (`association_id`),
  UNIQUE KEY `UQ_PROD_ALLERGEN` (`product_id`,`allergen_code`),
  KEY `allergen_code` (`allergen_code`),
  CONSTRAINT `product_allergen_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `product_allergen_ibfk_2` FOREIGN KEY (`allergen_code`) REFERENCES `m_allergen` (`allergen_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品アレルゲン';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_allergen`
--

LOCK TABLES `product_allergen` WRITE;
/*!40000 ALTER TABLE `product_allergen` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_allergen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category_relation`
--

DROP TABLE IF EXISTS `product_category_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category_relation` (
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `category_id` int NOT NULL COMMENT 'カテゴリーID',
  PRIMARY KEY (`product_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品とカテゴリーの紐付け';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category_relation`
--

LOCK TABLES `product_category_relation` WRITE;
/*!40000 ALTER TABLE `product_category_relation` DISABLE KEYS */;
INSERT INTO `product_category_relation` VALUES (1,2),(2,1),(3,2),(4,1),(4,3),(5,1),(6,1),(6,2),(7,1),(7,2),(8,1),(9,1),(9,2),(10,1);
/*!40000 ALTER TABLE `product_category_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `member_id` bigint NOT NULL COMMENT 'FK: MEMBERテーブルを参照',
  `product_id` bigint NOT NULL COMMENT 'FK: PRODUCTテーブルを参照',
  `rating` tinyint(1) NOT NULL DEFAULT '3' COMMENT '1〜5段階評価など',
  `comment` text,
  `posted_at` datetime NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `member_id` (`member_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品レビュー';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipping_address`
--

DROP TABLE IF EXISTS `shipping_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipping_address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主キー',
  `member_id` bigint NOT NULL COMMENT 'FK: MEMBERテーブルを参照',
  `recipient_name` varchar(100) NOT NULL COMMENT '配送先の氏名',
  `postal_code` varchar(8) NOT NULL,
  `full_address` varchar(500) NOT NULL COMMENT '配送先住所全般',
  `phone_number` varchar(15) NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0: 通常, 1: デフォルトの住所',
  PRIMARY KEY (`address_id`),
  KEY `member_id` (`member_id`),
  CONSTRAINT `shipping_address_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ギフト配送先';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipping_address`
--

LOCK TABLES `shipping_address` WRITE;
/*!40000 ALTER TABLE `shipping_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipping_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cart`
--

DROP TABLE IF EXISTS `t_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cart` (
  `CART_ID` int NOT NULL AUTO_INCREMENT COMMENT 'カートデータを一意に識別',
  `USER_ID` bigint DEFAULT NULL COMMENT 'ログインユーザーの場合に紐付け',
  `SESSION_ID` varchar(255) DEFAULT NULL COMMENT '非ログインユーザーのカート情報保持用',
  `PROD_ID` bigint NOT NULL COMMENT 'M_PRODUCTへの外部キー',
  `QUANTITY` int NOT NULL COMMENT 'ユーザーが選択した購入数量',
  `UNIT_PRICE` decimal(10,2) NOT NULL COMMENT 'カート投入時の単価（価格変動対策）',
  `OPTION_ID` bigint DEFAULT NULL COMMENT 'M_GIFT_OPTIONへの外部キー (例: ラッピング)',
  `OPTION_PRICE` decimal(10,2) DEFAULT NULL COMMENT '選択したオプションの価格',
  `ADD_DATE` datetime NOT NULL COMMENT 'カート投入日時',
  PRIMARY KEY (`CART_ID`),
  KEY `USER_ID` (`USER_ID`),
  KEY `PROD_ID` (`PROD_ID`),
  KEY `OPTION_ID` (`OPTION_ID`),
  CONSTRAINT `t_cart_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `member` (`member_id`),
  CONSTRAINT `t_cart_ibfk_2` FOREIGN KEY (`PROD_ID`) REFERENCES `product` (`product_id`),
  CONSTRAINT `t_cart_ibfk_3` FOREIGN KEY (`OPTION_ID`) REFERENCES `gift_option` (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='カート情報';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cart`
--

LOCK TABLES `t_cart` WRITE;
/*!40000 ALTER TABLE `t_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_delivery`
--

DROP TABLE IF EXISTS `t_delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_delivery` (
  `DELIVERY_ID` int NOT NULL AUTO_INCREMENT COMMENT '配送先を一意に識別',
  `ORDER_ID` int NOT NULL COMMENT 'T_ORDERへの外部キー',
  `RECIPIENT_NAME` varchar(100) NOT NULL COMMENT '受取人氏名',
  `POSTAL_CODE` varchar(10) NOT NULL,
  `ADDRESS` varchar(500) NOT NULL,
  `PHONE_NUMBER` varchar(20) NOT NULL,
  `DELIVERY_DATE` date DEFAULT NULL COMMENT '配送希望日',
  `TIME_SLOT` int DEFAULT NULL COMMENT 'M_DELIVERY_TIME_SLOTへのFK',
  `SHIPPING_TYPE_CODE` int NOT NULL COMMENT 'FK: M_SHIPPING_TYPE。実際に適用された運送形態。',
  `DELIVERY_STATUS` int DEFAULT NULL COMMENT '1:準備中, 2:出荷完了など',
  PRIMARY KEY (`DELIVERY_ID`),
  UNIQUE KEY `ORDER_ID` (`ORDER_ID`),
  KEY `TIME_SLOT` (`TIME_SLOT`),
  KEY `SHIPPING_TYPE_CODE` (`SHIPPING_TYPE_CODE`),
  CONSTRAINT `t_delivery_ibfk_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `t_order` (`ORDER_ID`),
  CONSTRAINT `t_delivery_ibfk_2` FOREIGN KEY (`TIME_SLOT`) REFERENCES `m_delivery_time_slot` (`TIME_SLOT_CODE`),
  CONSTRAINT `t_delivery_ibfk_3` FOREIGN KEY (`SHIPPING_TYPE_CODE`) REFERENCES `m_shipping_type` (`TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配送先情報';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_delivery`
--

LOCK TABLES `t_delivery` WRITE;
/*!40000 ALTER TABLE `t_delivery` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `ORDER_ID` int NOT NULL AUTO_INCREMENT COMMENT '注文を一意に識別',
  `USER_ID` bigint DEFAULT NULL COMMENT 'MEMBERへの外部キー (会員)',
  `ORDER_DATE` datetime NOT NULL COMMENT '注文確定日時',
  `TOTAL_AMOUNT` decimal(10,2) NOT NULL COMMENT '最終支払金額（送料・税込み）',
  `PROD_SUBTOTAL` decimal(10,2) NOT NULL COMMENT '商品金額のみの合計',
  `SHIPPING_FEE` decimal(10,2) NOT NULL COMMENT '送料',
  `TAX` decimal(10,2) NOT NULL COMMENT '消費税',
  `COUPON_ID` int DEFAULT NULL COMMENT '利用したクーポンがあれば',
  `STATUS` int NOT NULL COMMENT '1:未処理, 2:発送準備中など',
  PRIMARY KEY (`ORDER_ID`),
  KEY `USER_ID` (`USER_ID`),
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='注文ヘッダ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_detail`
--

DROP TABLE IF EXISTS `t_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_detail` (
  `DETAIL_ID` int NOT NULL AUTO_INCREMENT COMMENT '明細を一意に識別',
  `ORDER_ID` int NOT NULL COMMENT 'T_ORDERへの外部キー',
  `PROD_ID` bigint NOT NULL COMMENT '注文された商品ID',
  `PROD_NAME` varchar(255) NOT NULL COMMENT '注文時の商品名（変更履歴対策）',
  `UNIT_PRICE` decimal(10,2) NOT NULL COMMENT '注文時の単価',
  `QUANTITY` int NOT NULL COMMENT '注文数量',
  `GIFT_OPTION` varchar(255) DEFAULT NULL COMMENT 'ラッピング等のオプション詳細',
  PRIMARY KEY (`DETAIL_ID`),
  KEY `ORDER_ID` (`ORDER_ID`),
  KEY `PROD_ID` (`PROD_ID`),
  CONSTRAINT `t_order_detail_ibfk_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `t_order` (`ORDER_ID`),
  CONSTRAINT `t_order_detail_ibfk_2` FOREIGN KEY (`PROD_ID`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='注文明細';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_detail`
--

LOCK TABLES `t_order_detail` WRITE;
/*!40000 ALTER TABLE `t_order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_payment`
--

DROP TABLE IF EXISTS `t_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_payment` (
  `PAYMENT_ID` int NOT NULL AUTO_INCREMENT COMMENT '決済を一意に識別',
  `ORDER_ID` int NOT NULL COMMENT 'T_ORDERへの外部キー',
  `STATUS` int NOT NULL COMMENT '1:認証済, 2:売上確定など',
  `SERVICE_TRANS_ID` varchar(100) DEFAULT NULL COMMENT '決済代行会社が付与するID',
  `CARD_TYPE` int DEFAULT NULL COMMENT '1:VISA, 2:Masterなど',
  `CARD_LAST_4` varchar(4) DEFAULT NULL COMMENT '参照用',
  PRIMARY KEY (`PAYMENT_ID`),
  UNIQUE KEY `ORDER_ID` (`ORDER_ID`),
  CONSTRAINT `t_payment_ibfk_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `t_order` (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='決済情報';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_payment`
--

LOCK TABLES `t_payment` WRITE;
/*!40000 ALTER TABLE `t_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_payment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-23 15:03:36
