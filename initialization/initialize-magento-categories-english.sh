#!/bin/bash

server=$1
username=$2
password=$3
root_id=$4
storeViewCN=$5

adminToken=$(curl --location --request POST ${server}/index.php/rest/V1/integration/admin/token \
--header 'Content-Type: application/json' \
--header 'Cookie: PHPSESSID=f52b8b5466245cc1e0e333f8ae88617f' \
--data-raw '{
	"username": "'"$username"'",
	"password": "'"$password"'"
}' | jq -r .)

# post to default store view
PostCategory () {
  echo $(curl --location --request POST ${server}/index.php/rest/V1/categories \
  --header 'Authorization: Bearer '$adminToken'' \
  --header 'Content-Type: application/json;charset=utf-8' \
  --header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
  --data-raw '{
    "category": {
      "parent_id": "'"$1"'",
      "name": "'"$2"'",
      "is_active": true,
      "include_in_menu": true,
      "custom_attributes": [
        {
            "attribute_code": "description",
            "value": "'"${3:-Please add Description}"'"
        }
      ]
    }
  }' | jq -r .id)
}

# post to other store view like cn, en
PostCategoryWithId () {
  echo $(curl --location --request POST ${server}/index.php/rest/${1}/V1/categories \
  --header 'Authorization: Bearer '$adminToken'' \
  --header 'Content-Type: application/json;charset=utf-8' \
  --header 'Cookie: PHPSESSID=a6d1e4becbed70cbf9c57ba41cf15b5b' \
  --data-raw '{
    "category": {
      "id": "'"$2"'",
      "name": "'"$3"'",
      "is_active": true,
      "include_in_menu": true,
      "custom_attributes": [
        {
            "attribute_code": "description",
            "value": "'"${4:-Please add Description}"'"
        }
      ]
    }
  }' | jq -r .name)
}


#Add GFashion Root Level Category
# gf_id_en=$(PostCategory $root_id "Gfashion" "")
gf_id_en=789


#echo $gf_id_en

#Add GFashion First Level Category
men_id_en=$(PostCategory $gf_id_en "MEN" "")
PostCategoryWithId $storeViewCN $men_id_en "男士" ""
women_id_en=$(PostCategory $gf_id_en "WOMEN" "")
PostCategoryWithId $storeViewCN $women_id_en "女士" ""
crafts_id_en=$(PostCategory $gf_id_en "CRAFTS" "")
PostCategoryWithId $storeViewCN $crafts_id_en "工艺" ""
industries_id_en=$(PostCategory $gf_id_en "INDUSTRIES" "")
PostCategoryWithId $storeViewCN $industries_id_en "工业" ""


# Add Second Level Categories Englaish
men_cloth_id_en=$(PostCategory $men_id_en "CLOTHING" "Pragmatism and versatility connect the Fall collections on offer from our menswear designers, with modern meshings of shrewd tailoring, performance-driven athleticwear, and experimentally proportioned sportswear making up an adaptable whole. Outerwear brid_enges the traditional and the technical. Hooded anoraks, raincoats, and down-filled puffer jackets rendered in technical materials exhibit hyper-utilitarian engineering, while perennial wool peacoats, leather motorcycle jackets, and shearling jackets answer all style scenarios. For the tailoring-inclined, shirts and suiting in purist forms get elevated to statement status with bondage details, straps, and buckle accents, while graphic embellishments and embroid_enery adorn quilted bombers, wool and cashmere cardigans, and crewneck pullovers. Taking streetwear into new contexts are tracksuits, zip-ups, and hoodies that offer subculture symbolism and technical invention. Denim has a grunge appeal: Sulfur washes and ripped distressing are confid_enently casual, and tapered, wid_ene-leg, and cropped fits exude an eclectic new masculinity.")
men_bags_id_en=$(PostCategory $men_id_en "BAGS" "The vivid_en landscape of contemporary men’s bags reflects the equalizing of carrying aesthetics across gender lines, as well as the general post-normcore cultural drift towards the appeal of survivalist utilitarian ensembles. Designer backpacks and travel bags strive to performance-driven details and technical fabrics or leathers, with a balanced blend of form and function working towards appeal and premium durability. Messenger bags, document cases, pouches, and briefcases pair craftsmanship with elevated design, engineered for durability and enduring aesthetic appreciability. Familiar forms are refined and updated with aplomb. Playful and nostalgic elements prevail elsewhere, with deconstruction, unexpected graphical elements, and inventive silhouettes demanding attention and cognitive engagement. Cotton twill, denim, and technical canvas totes address the seemingly never-ending tote fever of postmodern city life, with printed graphics and signature house details bringing artful accents into the realm of the everyday practical implement.")
men_accessories_id_en=$(PostCategory $men_id_en "Accessories" "Treading a line between high-end appeal and irreverent attitude, our selection of menswear accessories runs the gamut from quiet and minimalist refinement to audacious creativity. Wid_ene-brimmed fedoras, harness belts, and studded wraparound bracelets complement fringed scarves, cozy knit beanies, and fingerless gloves, while travel-ready timepieces, modernist eyewear, and iPhone cases showcase the futuristic touches of everyday lifestyle. Pocket essentials including bifold wallets, embossed card holders, money clips, and keychains enhance clean and simple design with subtly tongue-in-cheek details, in contrast to the rugged delicacy of understated necklaces and masculine carved rings. Together, they define a new and contemporary vision of elegance.")
men_shoes_id_en=$(PostCategory $men_id_en "Shoes" "Concurrently embodying a function-driven design aesthetic as well as a favorable retention of streetwise appeal, designer men's shoes exhibit countless stylistic opportunities for any wearer. Thoroughly consid_enered low-top lace-ups and slip-on skate shoes echo tech-utilitarianism with futuristic forms and innovative fabrications. Minimalist sensibilities are strengthened through modern staples. Artisanal constructions of classic oxfords, derby lace ups, and loafers are not only rendered in sumptuous leathers, but also feature eye-catching buckles and contrasting panel accents. Seasonal favorites such as sandals and espadrilles defy simple conventions to redefine themselves and their own connotative value. Rugged sophistication is afforded to the wearer via clever Chelsea boots which value comfort as much as they do luxury. With contemporary men's designer shoes it is not a matter of making a statement, but rather understanding what statement you would like to make.")

women_cloth_id_en=$(PostCategory $women_id_en "CLOTHING" "Ingenuity and refinement mark the Fall offerings of our womenswear designers, with pairings of unconventional tailoring, athletic-inspired loungewear, and graphic streetwear advancing a fluid_en approach to modern dressing. A diverse range of outerwear sees wool peacoats, trench coats, and shearling jackets hang alongsid_ene sportswear-inspired bombers, varsity jackets, down-filled puffers, and military parkas. With casual basics, eclecticism predominates. T-shirts, sweatshirts, button-ups, and leather jackets are playfully accented with velvet, fur, and crystal, while jeans in distressed and reconstructed vintage denim evoke past eras. Offsetting the feminine elegance of pleated mid_eni skirts, draped blouses, and adventurously proportioned dresses is the androgynous spirit of unisex-styled hoodies, graphic t-shirts, and tracksuits. Athletic loungewear favors technical constructions and performance-minded textiles, while wid_ene-leg trousers, silk slip dresses, and oversized wool and cashmere sweaters bring fluid_enity to asymmetrically cut dresses, tailored jumpsuits and blazers, and fitted knitwear.")
women_bags_id_en=$(PostCategory $women_id_en "BAGS" "Our selection of women’s bags is defined by directional design and exceptional finishing. Cult favorite shapes, offered in an array of signature colors, make modern statements incorporating bold hardware, metallic finishes, and trademark detailing. Embellished clutches and zip pouches showcase exotic leathers, quilting, and studded accents, while go-to shoulder bags and duffles take a minimalist approach. Messenger bags, satchels, and document cases constructed from durable high-grade leathers similarly answer the demand for functionality by pairing expert craftsmanship with a refined aesthetic. Familiar styles balance practicality with unexpected detailing: Tote bags, backpacks, and bucket bags in unexpected textures feature playful appliqués and miniature logo charms")
women_accessories_id_en=$(PostCategory $women_id_en "ACCESSORIES" "Taking in fine jewelry ateliers, modern milliners, and avant-garde eyewear labs alike, our selection of women’s accessories answers every style scenario. Necklaces, bracelets, and earrings range from the rawly artistic to the composed. Minimalist chokers and sculptural pendants complement the textures of leather bracelets and ornate drop earrings. Precious materials shine in sterling silver and gold-plated rings with pearl, diamond, and crystal details. Warming up Fall layers are textural wool and fur scarves, knit beanies, and long leather gloves. Sturdy leather belts and harnesses inject irreverence, while polished leather wallets and card holders evoke casual refinement. Wid_ene-brimmed felt fedoras and sculptural acetate sunglasses make for dramatic finishing touches, next to playfully eccentric tech accessories, colorful coin pouches, and fur keychains.")
women_shoes_id_en=$(PostCategory $women_id_en "SHOES" "Designer women's shoes advance a unique vision of modern accessorizing by effortlessly combining seamless functionality and undeniable refinement. Demonstratively vast in their array of options, there will undoubtedly be a shoe that fits for whatever the agenda demands. Eccentric accents are achieved through shearling, fur, crystal, and velvet embellished minimalist leather sneakers, while thick treaded soles paired with technical fabrics and utilitarian details punctuate performance-inspired athletic styles. Thigh-high boots and platform sandals make an impact whereas a delicate balance is drawn with minimalist mules and sleek high heels that anchor relaxed tailoring. Classic menswear-inspired derbys and oxfords, conceived in a variety of luxurious fabrications, highlight meticulous handcraft and unique detailing. Elsewhere, a sense of subculture symbolism finds strength in high-top sneakers and slip-on skate shoes.")



# Add Second Level Categories CN
PostCategoryWithId $storeViewCN $men_cloth_id_en "服装" "实用主义与多样的功能性串联起了我们的设计师男士秋装，新颖的设计理念囊括了精美的成衣、功能性优秀的运动装以及实验性剪裁风格的休闲服，令整个系列的搭配性十分出彩。 夹克与外套结合了传统经典元素和机能性工艺。 以机能性面料打造的连帽式套头夹克、雨衣以及羽绒服具备格外出色的实用性，常青款羊毛双排扣短大衣、机车皮衣和剪羊毛夹克则可以轻松驾驭任何风格。 精裁成衣方面，纯粹主义式的衬衫和西装通过绑带及搭扣等束缚风格的细节得到全面升华。 此外，图案的修饰、刺绣的点缀为绗缝飞行员夹克、羊毛和山羊绒质地的开衫以及圆领套头衫锦上添花。 颇具亚文化象征元素、以机能性的工艺与面料所打造的田径外套、拉链夹克和连帽衫为街头风格带来了新的空气。 经过酸洗以及破洞撕裂处理的牛仔裤所呈现的做脏做旧效果，令单品散发出自信的休闲气质；锥形、阔腿以及九分等各种剪裁则展现了兼收并蓄的男性气息。"
PostCategoryWithId $storeViewCN $men_bags_id_en "包袋" "时下鲜活的流行男士包袋市场反映出了这一随身单品在性别界限上日趋统一，以及后性冷淡风的文化风潮逐渐向生存主义风格的实用性服饰转型的趋势。 设计师品牌的双肩包和旅行箱大量采用功能性出色的细节设计以及机能性面料或皮革，版型和实用性的双重保障令包袋既易于搭配又经久耐用。 设计师在匠人工艺的基础上进行更为精妙的雕琢，令邮差包、文件袋、手拿包以及公文包为耐久的特性和经典的美学气息而生，在传统包型的精裁和改良方面可谓信手拈来。 以玩味十足且颇具怀旧气息的细节进行零星点缀，搭配解构设计、剑走偏锋的图案元素以及原创的版型设计，所呈现的是吸睛的造型以及品味上的认同感。 托特包的的热度似乎从未于后现代都市生活中退却，以斜纹棉布、单宁布以及功能性帆布打造的托特包继续为这一浪潮推波助澜；包身以印花图案和标志性的徽标细节所点缀，为这一日常单品增添了文艺的气息。"
PostCategoryWithId $storeViewCN $men_accessories_id_en "配饰" "在高端时尚的魅力和玩世不恭的态度之间独辟蹊径 —— 本季的男装配饰在内敛、简约、精致以及大胆创新的尺度之间信步游走。 宽边绅士帽、牛仔风腰带、铆钉手环与流苏围巾、随性而舒适的冷帽及露指手套间的搭配相得益彰；为旅行而生的腕表、现代化风格的镜框和 iPhone 手机壳则透露出流动于日常生活之下的未来感。 俏皮玩味的细节装点着双折钱包、压花卡包、钞票夹和钥匙扣等口袋配饰，令单品简洁干练的质感更为突出；相较之下，设计低调的项链和富有阳刚之美的雕花戒指则展现了粗犷之中的细腻。 不言而喻，本季配饰诠释了现代男士对优雅的全新追求。"
PostCategoryWithId $storeViewCN $men_shoes_id_en "鞋履" "本季的设计师男士鞋履既展现出功能至上的设计美学，亦保留了对街头风元素的青睐，为穿用者带来鱼龙百变的搭配选择。 系带低帮运动鞋、一脚蹬滑板鞋在整体设计上经过精心考量，由革新性材料打造并以未来感十足的鞋型示人，体现了科技感为基调的实用主义；而采用现代设计风格的单品则让极简主义美感更为突出。 经典款牛津鞋、系带德比鞋和乐福鞋采用高档皮革为原料、由手工打造而成，醒目的搭扣与撞色拼接等细节更为其锦上添花。 作为春夏人气单品，凉鞋与草编鞋绕开了稍显单调的常规路线，通过设计上的重塑以展现单品的内涵价值。 优雅而入时的切尔西靴兼具舒适度与奢华感，令穿用者流露出硬朗而成熟的气质。 搭配本季的设计师男士鞋履时，刻意营造某种风格并非重点，关键在于穿用者是否明白自己真正想要展现出怎样的气场。"

PostCategoryWithId $storeViewCN $women_cloth_id_en "服装" "本季的设计师女装以巧思慧心和精致优雅为主格调，配以新颖的剪裁手法、运动休闲服及街头风印花单品，恰如其分地呈现出现代女装的精髓。 林林总总的外套不仅囊括了羊毛海军大衣、风衣和皮毛一体夹克，亦包罗飞行员夹克、棒球夹克、羽绒服及军风派克大衣等诸多款式。 在日常基础单品的世界里，并蓄兼收、实穿百搭是重中之重 —— T 恤、卫衣、衬衫和皮夹克等单品与丝绒、皮毛或水钻的搭配妙趣横生；而仿旧牛仔裤、重制古着牛仔单品则带领人们的思绪飘回曾经逝去的年代。 迷笛百褶裙、垂褶衬衫及剪裁大胆的连衣裙映衬出婀娜身姿；而中性风格的卫衣、印花 T 恤和运动套装则迸发出奔放的力量感。 此外，运动休闲装强调机能性设计和高性能面料的组合，不对称连衣裙、精裁连身裤和西装外套、贴身针织衫展现别具一格的剪裁与比例，而阔腿裤、真丝吊带裙、大廓形羊毛或羊绒毛衣则以柔和的走线示人，透过阴柔与阳刚间的平衡调和呈现焕然一新的着装风尚。"
PostCategoryWithId $storeViewCN $women_bags_id_en "包袋" "本季精选女式包袋透过精良的做工，展现独树一帜的风格与个性。 设计师采用一系列经典配色，令常青款式的人气持续升温；亮眼五金、金属感处理和徽标等细节则为单品画龙点睛。 由稀有皮革打造的手拿包和拉链手袋精致奢华，其上的绗缝设计与铆钉缀饰更丰富了单品的设计感；百搭的单肩包和行李包旨在追求本真、保留纯粹，传递着极简主义的时尚理念。 此外，邮差包、斜挎包和文件袋以结实耐用的高档皮革制作而成，将实用性、匠心工艺与细腻的美感融为一体。 新颖的图案、玩味的贴花和精巧的徽标配饰点缀于托特包、双肩包和水桶包之上，令实用而百搭的基础款式在亮眼细节的点缀下倍显与众不同。"
PostCategoryWithId $storeViewCN $women_accessories_id_en "配饰" "本季精选女式配饰囊括了高档首饰、潮流帽款和前卫镜架等多样单品，令时尚女性能够轻松驾驭各类场合。 其中，项链、手链和耳饰玲琅满目，既有简约精美的款式，亦不乏风格华丽的单品。 极简风格的颈链、雕塑感十足的项链与皮革手环、华美的耳坠遥相呼应，全方位展现搭配的层次感。 纯银和镀金戒指镶嵌着珍珠、钻石和水晶等珍贵宝石，晶莹剔透、众彩纷呈。 围巾采用拉绒羊毛或皮毛制作而成，可用于搭配针织毛线帽和长款皮革手套，在清冷深秋为穿戴者带来暖意。 结实耐用的皮革腰带和挽具配饰在实用的功能性上，为造型注入了慵懒随性的气质；而以抛光工艺打造的皮革钱包和卡包不仅精美别致，更流露轻松休闲的格调。 此外，宽檐毛毡绅士帽和醋酸纤维模塑太阳镜于设计上独出心裁，成为整套装束中的画龙点睛之笔；而俏皮的手机饰物、色彩纷呈的零钱袋和奢华皮毛钥匙扣等配饰则令造型胜在细节，于细微之中透露着新意。"
PostCategoryWithId $storeViewCN $women_shoes_id_en "鞋履" "设计师女鞋轻松融合出色的功能性及无与伦比的精美质感，为时尚造型带来别具一格的视觉享受。 本季鞋履款式丰富而多元 —— 潜心找寻，总能发掘到一双能够满足各种场合需求的百搭利器。 剪羊毛、毛皮、水钻或天鹅绒等材质的细节点缀令单品俏皮而玩味，为极简主义基调的皮革运动鞋锦上添花；沟纹厚底与机能面料、实用主义细节的搭配相得益彰，凸显运动风鞋履的灵动与卓越性能。 此外，高筒靴和厚底凉鞋所带来的视觉冲击力令人避无可避；极简主义穆勒鞋和优雅的高跟鞋则通过流畅的线条展现女性的精致。 自男装灵感中诞生的经典款德比鞋和牛津鞋则采用各式高档优质材料打造而成，精巧的手工艺和新奇别致的细节令单品脱颖而出；另一方面，高帮运动鞋和一脚蹬滑板鞋则淋漓尽致地传递出亚文化的精髓与魅力。"


# Add third & fourth Level Categories for Men English

men_tote_bags_id_en=$(PostCategory $men_bags_id_en "Tote Bags")
men_backpacks_id_en=$(PostCategory $men_bags_id_en "Backpacks")
men_pouches_document_holders_id_en=$(PostCategory $men_bags_id_en "Pouches & Document Holders")
men_briefcases_id_en=$(PostCategory $men_bags_id_en "Briefcases")
men_duffle_bags_id_en=$(PostCategory $men_bags_id_en "Duffle bags")
men_travel_bags_id_en=$(PostCategory $men_bags_id_en "Travel bags")
men_messenger_bags_id_en=$(PostCategory $men_bags_id_en "Messenger bags")

men_tops_id_en=$(PostCategory $men_cloth_id_en "Tops")
men_polos_id_en=$(PostCategory $men_tops_id_en "Polos")
men_t_shirt_id_en=$(PostCategory $men_tops_id_en "T-shirt")
men_henleys_id_en=$(PostCategory $men_tops_id_en "Henleys")
men_tank_tops_id_en=$(PostCategory $men_tops_id_en "Tank Tops")

men_underwear_loungewear_id_en=$(PostCategory $men_cloth_id_en "Underwear & Loungewear")
men_briefs_id_en=$(PostCategory $men_underwear_loungewear_id_en "Briefs")
men_boxers_id_en=$(PostCategory $men_underwear_loungewear_id_en "Boxers")
men_robes_id_en=$(PostCategory $men_underwear_loungewear_id_en "Robes")
men_pyjamas_loungewear_id_en=$(PostCategory $men_underwear_loungewear_id_en "Pyjamas & Loungewear")

men_sweaters_id_en=$(PostCategory $men_cloth_id_en "Sweaters")
men_v_necks_id_en=$(PostCategory $men_sweaters_id_en "V-necks")
men_crewnecks_id_en=$(PostCategory $men_sweaters_id_en "Crewnecks")
men_sweatshirts_id_en=$(PostCategory $men_sweaters_id_en "Sweatshirts")
men_cardigans_id_en=$(PostCategory $men_sweaters_id_en "Cardigans")
men_hoodies_zipups_id_en=$(PostCategory $men_sweaters_id_en "Hoodies & Zipups")
men_turtlenecks_id_en=$(PostCategory $men_sweaters_id_en "Turtlenecks")

men_jacket_coats_id_en=$(PostCategory $men_cloth_id_en "Jacket & Coats")
men_peacoats_id_en=$(PostCategory $men_jacket_coats_id_en "Peacoats")
men_coats_id_en=$(PostCategory $men_jacket_coats_id_en "Coats")
men_jackets_id_en=$(PostCategory $men_jacket_coats_id_en "Jackets")
men_denim_jackets_id_en=$(PostCategory $men_jacket_coats_id_en "Denim Jackets")
men_leather_jackets_id_en=$(PostCategory $men_jacket_coats_id_en "leather jackets")
men_fur_shearling_id_en=$(PostCategory $men_jacket_coats_id_en "fur & shearling")
men_down_id_en=$(PostCategory $men_jacket_coats_id_en "down")
men_trench_coats_id_en=$(PostCategory $men_jacket_coats_id_en "trench coats")
men_bombers_d_en=$(PostCategory $men_jacket_coats_id_en "bombers")
men_vests_id_en=$(PostCategory $men_jacket_coats_id_en "vests")

men_swimwear_id_en=$(PostCategory $men_cloth_id_en "swimwear")
men_swimsuits_id_en=$(PostCategory $men_swimwear_id_en "swimsuits")

men_jeans_id_en=$(PostCategory $men_cloth_id_en "jeans")
men_shorts_id_en=$(PostCategory $men_cloth_id_en "shorts")
men_shirts_id_en=$(PostCategory $men_cloth_id_en "shirts")

men_pants_id_en=$(PostCategory $men_cloth_id_en "pants")
men_cargo_pants_id_en=$(PostCategory $men_pants_id_en "cargo pants")
men_leather_pants_id_en=$(PostCategory $men_pants_id_en "leather pants")
men_sweatpants_id_en=$(PostCategory $men_pants_id_en "sweatpants")
men_trousers_id_en=$(PostCategory $men_pants_id_en "trousers")

men_suits_blazers_id_en=$(PostCategory $men_cloth_id_en "suits & blazers")
men_blazers_id_en=$(PostCategory $men_suits_blazers_id_en "blazers")
men_suits_id_en=$(PostCategory $men_suits_blazers_id_en "suits")
men_tuxedos_id_en=$(PostCategory $men_suits_blazers_id_en "tuxedos")
men_waistcoats_id_en=$(PostCategory $men_suits_blazers_id_en "waistcoats")

men_pocket_squares_tie_bars_id_en=$(PostCategory $men_accessories_id_en "pocket squares & tie bars")
men_scarves_id_en=$(PostCategory $men_accessories_id_en "scarves")
men_dog_accessories_id_en=$(PostCategory $men_accessories_id_en "dog accessories")

men_hats_id_en=$(PostCategory $men_accessories_id_en "hats")
men_structured_hats_id_en=$(PostCategory $men_hats_id_en "structured hats")
men_beanies_id_en=$(PostCategory $men_hats_id_en "beanies")
men_caps_flat_caps_id_en=$(PostCategory $men_hats_id_en "caps & Flat Caps")

men_gloves_id_en=$(PostCategory $men_accessories_id_en "gloves")
men_watches_id_en=$(PostCategory $men_accessories_id_en "watches")
men_towels_id_en=$(PostCategory $men_accessories_id_en "towels")

men_jewelry_id_en=$(PostCategory $men_accessories_id_en "jewelry")
men_rings_id_en=$(PostCategory $men_jewelry_id_en "rings")
men_bracelets_id_en=$(PostCategory $men_jewelry_id_en "bracelets")
men_earrings_id_en=$(PostCategory $men_jewelry_id_en "earrings")
men_pins_id_en=$(PostCategory $men_jewelry_id_en "pins")
men_cufflinks_id_en=$(PostCategory $men_jewelry_id_en "cufflinks")
men_necklaces_id_en=$(PostCategory $men_jewelry_id_en "necklaces")

men_TECH_id_en=$(PostCategory $men_accessories_id_en "TECH")
men_iphone_cases_id_en=$(PostCategory $men_TECH_id_en "iphone cases")
men_headphones_id_en=$(PostCategory $men_TECH_id_en "headphones")
men_laptop_cases_id_en=$(PostCategory $men_TECH_id_en "laptop cases")

men_eyewear_id_en=$(PostCategory $men_accessories_id_en "eyewear")
men_sunglasses_id_en=$(PostCategory $men_eyewear_id_en "sunglasses")
men_glasses_id_en=$(PostCategory $men_eyewear_id_en "glasses")

men_souvenirs_id_en=$(PostCategory $men_accessories_id_en "souvenirs")
men_posters_id_en=$(PostCategory $men_souvenirs_id_en "posters")
men_notes_sketches_id_en=$(PostCategory $men_souvenirs_id_en "notes & sketches")

men_belts_suspenders_id_en=$(PostCategory $men_accessories_id_en "belts & suspenders")
men_socks_id_en=$(PostCategory $men_accessories_id_en "socks")
men_blankets_id_en=$(PostCategory $men_accessories_id_en "blankets")
men_keychains_id_en=$(PostCategory $men_accessories_id_en "keychains")

men_wallets_card_holders_id_en=$(PostCategory $men_accessories_id_en "wallets & card holders")
men_card_holders_id_en=$(PostCategory $men_wallets_card_holders_id_en "card holders")
men_passport_holders_id_en=$(PostCategory $men_wallets_card_holders_id_en "passport holders")
men_wallets_id_en=$(PostCategory $men_wallets_card_holders_id_en "wallets")
men_money_clips_id_en=$(PostCategory $men_wallets_card_holders_id_en "money clips")

men_umbrellas_id_en=$(PostCategory $men_accessories_id_en "umbrellas")
men_ties_id_en=$(PostCategory $men_accessories_id_en "ties")
men_bow_ties_id_en=$(PostCategory $men_ties_id_en "bow ties")
men_neck_ties_id_en=$(PostCategory $men_ties_id_en "neck ties")

men_loafers_id_en=$(PostCategory $men_shoes_id_en "loafers")
men_sandals_id_en=$(PostCategory $men_shoes_id_en "sandals")
men_flip_flops_id_en=$(PostCategory $men_sandals_id_en "flip flops")
men_sandals_sandals_id_en=$(PostCategory $men_sandals_id_en "sandals")

men_monkstraps_id_en=$(PostCategory $men_shoes_id_en "monkstraps")
men_boat_shoes_Moccasins_id_en=$(PostCategory $men_shoes_id_en "boat shoes & Moccasins")
men_lace_ups_id_en=$(PostCategory $men_shoes_id_en "lace ups")
men_Espardrilles_id_en=$(PostCategory $men_shoes_id_en "Espardrilles")

men_sneakers_id_en=$(PostCategory $men_shoes_id_en "sneakers")
men_low_top_sneakers_id_en=$(PostCategory $men_sneakers_id_en "low top sneakers")
men_high_top_sneakers_id_en=$(PostCategory $men_sneakers_id_en "high top sneakers")

men_boots_id_en=$(PostCategory $men_shoes_id_en "boots")
men_wingtip_boots_id_en=$(PostCategory $men_boots_id_en "wingtip boots")
men_chelsea_boots_id_en=$(PostCategory $men_boots_id_en "chelsea boots")
men_zip_up_buckled_boots_id_en=$(PostCategory $men_boots_id_en "zip up & buckled boots")
men_biker_combat_boots_id_en=$(PostCategory $men_boots_id_en "biker & combat boots")
men_desert_boots_id_en=$(PostCategory $men_boots_id_en "desert boots")
men_lace_up_boots_id_en=$(PostCategory $men_boots_id_en "lace-up boots")

# Add third & fourth Level Categories for Women Engliash

women_tote_bags_id_en=$(PostCategory $women_bags_id_en "Tote Bags")
women_backpacks_id_en=$(PostCategory $women_bags_id_en "backpacks")
women_sholder_bags_id_en=$(PostCategory $women_bags_id_en "sholder bags")
women_clutched_pouches_id_en=$(PostCategory $women_bags_id_en "clutched & pouches")
women_clutches_id_en=$(PostCategory $women_clutched_pouches_id_en "clutches")
women_pouches_id_en=$(PostCategory $women_clutched_pouches_id_en "pouches")
women_duffle_top_handle_bags_id_en=$(PostCategory $women_bags_id_en "Duffle & top handle bags")
women_messenger_bags_satchels_id_en=$(PostCategory $women_bags_id_en "messenger bags & satchels")
women_travel_bags_id_en=$(PostCategory $women_bags_id_en "travel bags")

women_tops_id_en=$(PostCategory $women_cloth_id_en "Tops")
women_polos_id_en=$(PostCategory $women_tops_id_en "Polos")
women_t_shirt_id_en=$(PostCategory $women_tops_id_en "T-shirt")
women_blouses_id_en=$(PostCategory $women_tops_id_en "Blouses")
women_tank_tops_camisoles_id_en=$(PostCategory $women_tops_id_en "Tank Tops & Camisoles")
women_shirts_id_en=$(PostCategory $women_tops_id_en "Shirts")
women_bodysuits_id_en=$(PostCategory $women_tops_id_en "Bodysuits")

women_lingerie_id_en=$(PostCategory $women_cloth_id_en "Lingerie")
women_thongs_id_en=$(PostCategory $women_lingerie_id_en "thongs")
women_briefs_id_en=$(PostCategory $women_lingerie_id_en "briefs")
women_boy_shorts_id_en=$(PostCategory $women_lingerie_id_en "boy shorts")
women_bra_id_en=$(PostCategory $women_lingerie_id_en "bra")
women_robes_id_en=$(PostCategory $women_lingerie_id_en "robes")
women_sleepwear_id_en=$(PostCategory $women_lingerie_id_en "sleepwear")
women_corsets_bodysuits_id_en=$(PostCategory $women_lingerie_id_en "Corsets & Bodysuits")
women_tanks_id_en=$(PostCategory $women_lingerie_id_en "tanks")

women_skirts_id_en=$(PostCategory $women_cloth_id_en "Skirts")
women_mid_length_skirts_id_en=$(PostCategory $women_skirts_id_en "mid length skirts")
women_short_skirts_id_en=$(PostCategory $women_skirts_id_en "short skirts")
women_long_skirts_id_en=$(PostCategory $women_skirts_id_en "long skirts")

women_sweaters_id_en=$(PostCategory $women_cloth_id_en "sweaters")
women_v_necks_id_en=$(PostCategory $women_sweaters_id_en "V-necks")
women_crewnecks_id_en=$(PostCategory $women_sweaters_id_en "crewnecks")
women_sweatshirts_id_en=$(PostCategory $women_sweaters_id_en "sweatshirts")
women_cardigans_id_en=$(PostCategory $women_sweaters_id_en "cardigans")
women_hoodies_zipups_id_en=$(PostCategory $women_sweaters_id_en "hoodies & zipups")
women_turtlenecks_id_en=$(PostCategory $women_sweaters_id_en "turtlenecks")

women_jacket_coats_id_en=$(PostCategory $women_cloth_id_en "jacket & coats")
women_coats_id_en=$(PostCategory $women_jacket_coats_id_en "coats")
women_jackets_id_en=$(PostCategory $women_jacket_coats_id_en "jackets")
women_denim_jackets_id_en=$(PostCategory $women_jacket_coats_id_en "denim jackets")
women_leather_jackets_id_en=$(PostCategory $women_jacket_coats_id_en "leather jackets")
women_fur_shearling_id_en=$(PostCategory $women_jacket_coats_id_en "fur & shearling")
women_down_id_en=$(PostCategory $women_jacket_coats_id_en "down")
women_blazers_id_en=$(PostCategory $women_jacket_coats_id_en "blazers")
women_trench_coats_id_en=$(PostCategory $women_jacket_coats_id_en "trench coats")
women_bombers_id_en=$(PostCategory $women_jacket_coats_id_en "bombers")
women_vests_id_en=$(PostCategory $women_jacket_coats_id_en "vests")

women_swimwear_id_en=$(PostCategory $women_cloth_id_en "swimwear")
women_bikinis_id_en=$(PostCategory $women_swimwear_id_en "bikinis")
women_one_piece_id_en=$(PostCategory $women_swimwear_id_en "One-Piece")

women_jeans_id_en=$(PostCategory $women_cloth_id_en "jeans")
women_shorts_id_en=$(PostCategory $women_cloth_id_en "shorts")

women_pants_id_en=$(PostCategory $women_cloth_id_en "pants")
women_leggings_id_en=$(PostCategory $women_pants_id_en "leggings")
women_leather_pants_id_en=$(PostCategory $women_pants_id_en "leather pants")
women_sweatpants_id_en=$(PostCategory $women_pants_id_en "sweatpants")
women_trousers_id_en=$(PostCategory $women_pants_id_en "trousers")

women_activewear_id_en=$(PostCategory $women_cloth_id_en "Activewear")
women_active_tops_id_en=$(PostCategory $women_activewear_id_en "Tops")
women_hoodies_zipups_id_en=$(PostCategory $women_activewear_id_en "hoodies & zipups")
women_outerwear_id_en=$(PostCategory $women_activewear_id_en "outerwear")
women_sport_shorts_skirts_id_en=$(PostCategory $women_activewear_id_en "sport shorts & skirts")
women_active_leggings_id_en=$(PostCategory $women_activewear_id_en "leggings")
women_sports_bras_id_en=$(PostCategory $women_activewear_id_en "sports Bras")
women_active_pants_id_en=$(PostCategory $women_activewear_id_en "pants")
women_dresses_id_en=$(PostCategory $women_activewear_id_en "Dresses")

women_dresses_id_en=$(PostCategory $women_cloth_id_en "Dresses")
women_mid_length_dresses_id_en=$(PostCategory $women_dresses_id_en "mid length dresses")
women_short_dresses_id_en=$(PostCategory $women_dresses_id_en "short dresses")
women_long_dressses_id_en=$(PostCategory $women_dresses_id_en "long dressses")

women_jumpsuits_id_en=$(PostCategory $women_cloth_id_en "Jumpsuits")

women_bag_accessories_id_en=$(PostCategory $women_accessories_id_en "Bag Accessories")
women_cosmetic_cases_id_en=$(PostCategory $women_accessories_id_en "cosmetic cases")
women_scarves_id_en=$(PostCategory $women_accessories_id_en "scarves")
women_silks_cashmeres_en=$(PostCategory $women_scarves_id_en "silks & Cashmeres")
women_fur_shearling_a_id_en=$(PostCategory $women_scarves_id_en"fur & shearling")
women_knits_id_en=$(PostCategory $women_scarves_id_en "Knits")
women_dog_accessories_id_en=$(PostCategory $women_accessories_id_en "dog accessories")

women_hats_id_en=$(PostCategory $women_accessories_id_en "hats")
women_headbands_hair_Accessories_id_en=$(PostCategory $women_hats_id_en "Headbands & Hair Accessories")
women_beanies_id_en=$(PostCategory $women_hats_id_en "beanies")
women_caps_id_en=$(PostCategory $women_hats_id_en "Caps")
women_fedoras_panama_hats_id_en=$(PostCategory $women_hats_id_en "Fedoras & Panama Hats")
women_beach_hats_id_en=$(PostCategory $women_hats_id_en "Beach Hats")

women_gloves_id_en=$(PostCategory $women_accessories_id_en "gloves")
women_watches_id_en=$(PostCategory $women_accessories_id_en "watches")
women_towels_id_en=$(PostCategory $women_accessories_id_en "towels")

women_jewelry_id_en=$(PostCategory $women_accessories_id_en "jewelry")
women_rings_id_en=$(PostCategory $women_jewelry_id_en "rings")
women_bracelets_id_en=$(PostCategory $women_jewelry_id_en "bracelets")
women_earrings_id_en=$(PostCategory $women_jewelry_id_en "earrings")
women_brooches_id_en=$(PostCategory $women_jewelry_id_en "Brooches")
women_cufflinks_id_en=$(PostCategory $women_jewelry_id_en "cufflinks")
women_necklaces_id_en=$(PostCategory $women_jewelry_id_en "necklaces")

women_TECH_id_en=$(PostCategory $women_accessories_id_en "TECH")
women_iphone_cases_id_en=$(PostCategory $women_TECH_id_en "iphone cases")

women_eyewear_id_en=$(PostCategory $women_accessories_id_en "eyewear")
women_sunglasses_id_en=$(PostCategory $women_eyewear_id_en "sunglasses")
women_glasses_id_en=$(PostCategory $women_eyewear_id_en "glasses")

women_belts_suspenders_id_en=$(PostCategory $women_accessories_id_en "belts & suspenders")
women_socks_id_en=$(PostCategory $women_accessories_id_en "socks")
women_blankets_id_en=$(PostCategory $women_accessories_id_en "blankets")
women_keychains_id_en=$(PostCategory $women_accessories_id_en "keychains")

women_wallets_card_holders_id_en=$(PostCategory $women_accessories_id_en "wallets & card holders")
women_card_holders_id_en=$(PostCategory $women_wallets_card_holders_id_en "card holders")
women_passport_holders_id_en=$(PostCategory $women_wallets_card_holders_id_en "passport holders")
women_wallets_id_en=$(PostCategory $women_wallets_card_holders_id_en "wallets")
women_coin_pouches_id_en=$(PostCategory $women_wallets_card_holders_id_en "coin Pouches")

women_umbrellas_id_en=$(PostCategory $women_accessories_id_en "umbrellas")
women_fine_jewelry_id_en=$(PostCategory $women_accessories_id_en "Fine Jewelry")
women_fine_rings_id_en=$(PostCategory $women_fine_jewelry_id_en "rings")
women_fine_earrings_id_en=$(PostCategory $women_fine_jewelry_id_en "earrings")

women_sandals_id_en=$(PostCategory $women_shoes_id_en "sandals")
women_flat_sandals_id_en=$(PostCategory $women_sandals_id_en "flat sandals")
women_heeled_sandals_id_en=$(PostCategory $women_sandals_id_en "Heeled sandals")

women_flats_id_en=$(PostCategory $women_shoes_id_en "flats")
women_slippers_loafers_id_en=$(PostCategory $women_flats_id_en "slippers & loafers")
women_lace_up_oxfords_id_en=$(PostCategory $women_flats_id_en "lace up & oxfords")
women_ballerina_flats_id_en=$(PostCategory $women_flats_id_en "ballerina flats")
women_espadrilles_id_en=$(PostCategory $women_flats_id_en "espadrilles")

women_sneakers_id_en=$(PostCategory $women_shoes_id_en "sneakers")
women_low_top_sneakers_id_en=$(PostCategory $women_sneakers_id_en "low top sneakers")
women_high_top_sneakers_id_en=$(PostCategory $women_sneakers_id_en "high top sneakers")

women_boots_id_en=$(PostCategory $women_shoes_id_en "boots")
women_mid_calf_boots_id_en=$(PostCategory $women_boots_id_en "mid-calf boots")
women_ankle_boots_id_en=$(PostCategory $women_boots_id_en "ankle boots")
women_tall_boots_id_en=$(PostCategory $women_boots_id_en "tall boots")

women_heels_id_en=$(PostCategory $women_shoes_id_en "heels")


# Add third & fourth Level Categories for Men Simple Chinese

PostCategoryWithId $storeViewCN $men_tote_bags_id_en "tote托特包"
PostCategoryWithId $storeViewCN $men_backpacks_id_en "双肩包"
PostCategoryWithId $storeViewCN $men_pouches_document_holders_id_en "手拿包及文件袋"
PostCategoryWithId $storeViewCN $men_briefcases_id_en "公文包"
PostCategoryWithId $storeViewCN $men_duffle_bags_id_en "旅行包"
PostCategoryWithId $storeViewCN $men_travel_bags_id_en "旅行箱"
PostCategoryWithId $storeViewCN $men_messenger_bags_id_en "邮差包"

PostCategoryWithId $storeViewCN $men_tops_id_en "上衣"
PostCategoryWithId $storeViewCN $men_polos_id_en "Polo衫"
PostCategoryWithId $storeViewCN $men_t_shirt_id_en "T恤"
PostCategoryWithId $storeViewCN $men_henleys_id_en "亨利衫"
PostCategoryWithId $storeViewCN $men_tank_tops_id_en "背心"

PostCategoryWithId $storeViewCN $men_underwear_loungewear_id_en "内衣及居家服"
PostCategoryWithId $storeViewCN $men_briefs_id_en "三角内裤"
PostCategoryWithId $storeViewCN $men_boxers_id_en "平角内裤"
PostCategoryWithId $storeViewCN $men_robes_id_en "浴袍"
PostCategoryWithId $storeViewCN $men_pyjamas_loungewear_id_en "睡衣及居家服"

PostCategoryWithId $storeViewCN $men_sweaters_id_en "卫衣及针织衫"
PostCategoryWithId $storeViewCN $men_v_necks_id_en "V领衫"
PostCategoryWithId $storeViewCN $men_crewnecks_id_en "圆领衫"
PostCategoryWithId $storeViewCN $men_sweatshirts_id_en "套头衫"
PostCategoryWithId $storeViewCN $men_cardigans_id_en "开衫"
PostCategoryWithId $storeViewCN $men_hoodies_zipups_id_en "连帽衫及拉链衫"
PostCategoryWithId $storeViewCN $men_turtlenecks_id_en "高领衫"

PostCategoryWithId $storeViewCN $men_jacket_coats_id_en "夹克及大衣"
PostCategoryWithId $storeViewCN $men_peacoats_id_en "双排扣大衣"
PostCategoryWithId $storeViewCN $men_coats_id_en "大衣"
PostCategoryWithId $storeViewCN $men_jackets_id_en "夹克"
PostCategoryWithId $storeViewCN $men_denim_jackets_id_en "牛仔夹克"
PostCategoryWithId $storeViewCN $men_leather_jackets_id_en "皮夹克"
PostCategoryWithId $storeViewCN $men_fur_shearling_id_en "皮草"
PostCategoryWithId $storeViewCN $men_down_id_en "羽绒服"
PostCategoryWithId $storeViewCN $men_trench_coats_id_en "风衣"
PostCategoryWithId $storeViewCN $men_bombers_d_en "飞行员夹克"
PostCategoryWithId $storeViewCN $men_vests_id_en "马甲背心"

PostCategoryWithId $storeViewCN $men_swimwear_id_en "泳装"
PostCategoryWithId $storeViewCN $men_swimsuits_id_en "泳衣"

PostCategoryWithId $storeViewCN $men_jeans_id_en "牛仔裤"
PostCategoryWithId $storeViewCN $men_shorts_id_en "短裤"
PostCategoryWithId $storeViewCN $men_shirts_id_en "衬衫"

PostCategoryWithId $storeViewCN $men_pants_id_en "裤子"
PostCategoryWithId $storeViewCN $men_cargo_pants_id_en "工装裤"
PostCategoryWithId $storeViewCN $men_leather_pants_id_en "皮裤"
PostCategoryWithId $storeViewCN $men_sweatpants_id_en "运动裤"
PostCategoryWithId $storeViewCN $men_trousers_id_en "长裤"

PostCategoryWithId $storeViewCN $men_suits_blazers_id_en "西服套装及西装外套"
PostCategoryWithId $storeViewCN $men_blazers_id_en "晚装礼服"
PostCategoryWithId $storeViewCN $men_suits_id_en "西服套装"
PostCategoryWithId $storeViewCN $men_tuxedos_id_en "西服外套"
PostCategoryWithId $storeViewCN $men_waistcoats_id_en "马甲"

PostCategoryWithId $storeViewCN $men_pocket_squares_tie_bars_id_en "口袋方巾及领带夹"
PostCategoryWithId $storeViewCN $men_scarves_id_en "围巾"
PostCategoryWithId $storeViewCN $men_dog_accessories_id_en "宠物配饰"

PostCategoryWithId $storeViewCN $men_hats_id_en "帽子"
PostCategoryWithId $storeViewCN $men_structured_hats_id_en "全沿帽"
PostCategoryWithId $storeViewCN $men_beanies_id_en "套头帽"
PostCategoryWithId $storeViewCN $men_caps_flat_caps_id_en "棒球帽及鸭舌帽"

PostCategoryWithId $storeViewCN $men_gloves_id_en "手套"
PostCategoryWithId $storeViewCN $men_watches_id_en "手表"
PostCategoryWithId $storeViewCN $men_towels_id_en "毛巾"

PostCategoryWithId $storeViewCN $men_jewelry_id_en "珠宝首饰"
PostCategoryWithId $storeViewCN $men_rings_id_en "戒指"
PostCategoryWithId $storeViewCN $men_bracelets_id_en "手链"
PostCategoryWithId $storeViewCN $men_earrings_id_en "耳环"
PostCategoryWithId $storeViewCN $men_pins_id_en "胸针"
PostCategoryWithId $storeViewCN $men_cufflinks_id_en "袖口"
PostCategoryWithId $storeViewCN $men_necklaces_id_en "项链"

PostCategoryWithId $storeViewCN $men_TECH_id_en "电子产品及配件"
PostCategoryWithId $storeViewCN $men_iphone_cases_id_en "Iphone保护套"
PostCategoryWithId $storeViewCN $men_headphones_id_en "头戴耳机"
PostCategoryWithId $storeViewCN $men_laptop_cases_id_en "笔记本电脑保护套"

PostCategoryWithId $storeViewCN $men_eyewear_id_en "眼镜"
PostCategoryWithId $storeViewCN $men_sunglasses_id_en "太阳镜"
PostCategoryWithId $storeViewCN $men_glasses_id_en "眼镜"

PostCategoryWithId $storeViewCN $men_souvenirs_id_en "纪念品"
PostCategoryWithId $storeViewCN $men_posters_id_en "海报"
PostCategoryWithId $storeViewCN $men_notes_sketches_id_en "笔记本及速写本"

PostCategoryWithId $storeViewCN $men_belts_suspenders_id_en "腰带及背带"
PostCategoryWithId $storeViewCN $men_socks_id_en "袜子"
PostCategoryWithId $storeViewCN $men_blankets_id_en "被毯"
PostCategoryWithId $storeViewCN $men_keychains_id_en "钥匙扣"

PostCategoryWithId $storeViewCN $men_wallets_card_holders_id_en "钱包及卡包"
PostCategoryWithId $storeViewCN $men_card_holders_id_en "卡包"
PostCategoryWithId $storeViewCN $men_passport_holders_id_en "护照套"
PostCategoryWithId $storeViewCN $men_wallets_id_en "钱包"
PostCategoryWithId $storeViewCN $men_money_clips_id_en "钱夹"

PostCategoryWithId $storeViewCN $men_umbrellas_id_en "雨伞"
PostCategoryWithId $storeViewCN $men_ties_id_en "领带"
PostCategoryWithId $storeViewCN $men_bow_ties_id_en "领带"
PostCategoryWithId $storeViewCN $men_neck_ties_id_en "领结"

PostCategoryWithId $storeViewCN $men_loafers_id_en "乐福鞋"
PostCategoryWithId $storeViewCN $men_sandals_id_en "凉鞋"
PostCategoryWithId $storeViewCN $men_flip_flops_id_en "人字拖"
PostCategoryWithId $storeViewCN $men_sandals_sandals_id_en "凉鞋"

PostCategoryWithId $storeViewCN $men_monkstraps_id_en "孟轲鞋"
PostCategoryWithId $storeViewCN $men_boat_shoes_Moccasins_id_en "帆船鞋及莫卡辛鞋"
PostCategoryWithId $storeViewCN $men_lace_ups_id_en "系带鞋"
PostCategoryWithId $storeViewCN $men_Espardrilles_id_en "草编鞋"

PostCategoryWithId $storeViewCN $men_sneakers_id_en "运动鞋"
PostCategoryWithId $storeViewCN $men_low_top_sneakers_id_en "低帮运动鞋"
PostCategoryWithId $storeViewCN $men_high_top_sneakers_id_en "高帮运动鞋"

PostCategoryWithId $storeViewCN $men_boots_id_en "靴子"
PostCategoryWithId $storeViewCN $men_wingtip_boots_id_en "全雕花靴"
PostCategoryWithId $storeViewCN $men_chelsea_boots_id_en "切尔西靴"
PostCategoryWithId $storeViewCN $men_zip_up_buckled_boots_id_en "拉链靴及扣带靴"
PostCategoryWithId $storeViewCN $men_biker_combat_boots_id_en "机车靴及军靴"
PostCategoryWithId $storeViewCN $men_desert_boots_id_en "沙漠靴"
PostCategoryWithId $storeViewCN $men_lace_up_boots_id_en "系带靴"


# Add third & fourth Level Categories for Women Engliash

PostCategoryWithId $storeViewCN $women_tote_bags_id_en "tote托特包"
PostCategoryWithId $storeViewCN $women_backpacks_id_en "双肩包"
PostCategoryWithId $storeViewCN $women_sholder_bags_id_en "单肩包"
PostCategoryWithId $storeViewCN $women_clutched_pouches_id_en "手拿包及手袋"
PostCategoryWithId $storeViewCN $women_clutches_id_en "手拿包"
PostCategoryWithId $storeViewCN $women_pouches_id_en "手袋"
PostCategoryWithId $storeViewCN $women_duffle_top_handle_bags_id_en "行李包及手提包"
PostCategoryWithId $storeViewCN $women_messenger_bags_satchels_id_en "邮差包及书包"
PostCategoryWithId $storeViewCN $women_travel_bags_id_en "行李箱"

PostCategoryWithId $storeViewCN $women_tops_id_en "上衣"
PostCategoryWithId $storeViewCN $women_polos_id_en "Polo衫"
PostCategoryWithId $storeViewCN $women_t_shirt_id_en "T恤"
PostCategoryWithId $storeViewCN $women_blouses_id_en "女士衬衫"
PostCategoryWithId $storeViewCN $women_tank_tops_camisoles_id_en "背心及吊带"
PostCategoryWithId $storeViewCN $women_shirts_id_en "衬衫"
PostCategoryWithId $storeViewCN $women_bodysuits_id_en "连体衣"

PostCategoryWithId $storeViewCN $women_lingerie_id_en "内衣"
PostCategoryWithId $storeViewCN $women_thongs_id_en "丁字裤"
PostCategoryWithId $storeViewCN $women_briefs_id_en "三角内裤"
PostCategoryWithId $storeViewCN $women_boy_shorts_id_en "平角内裤"
PostCategoryWithId $storeViewCN $women_bra_id_en "文胸"
PostCategoryWithId $storeViewCN $women_robes_id_en "浴袍"
PostCategoryWithId $storeViewCN $women_sleepwear_id_en "睡衣"
PostCategoryWithId $storeViewCN $women_corsets_bodysuits_id_en "紧身胸衣及连体衣"
PostCategoryWithId $storeViewCN $women_tanks_id_en "背心"

PostCategoryWithId $storeViewCN $women_skirts_id_en "半身裙"
PostCategoryWithId $storeViewCN $women_mid_length_skirts_id_en "中长裙"
PostCategoryWithId $storeViewCN $women_short_skirts_id_en "短裙"
PostCategoryWithId $storeViewCN $women_long_skirts_id_en "长裙"

PostCategoryWithId $storeViewCN $women_sweaters_id_en "卫衣及针织衫"
PostCategoryWithId $storeViewCN $women_v_necks_id_en "V领衫"
PostCategoryWithId $storeViewCN $women_crewnecks_id_en "圆领衫"
PostCategoryWithId $storeViewCN $women_sweatshirts_id_en "套头衫"
PostCategoryWithId $storeViewCN $women_cardigans_id_en "开衫"
PostCategoryWithId $storeViewCN $women_hoodies_zipups_id_en "连帽衫及拉链衫"
PostCategoryWithId $storeViewCN $women_turtlenecks_id_en "高领衫"

PostCategoryWithId $storeViewCN $women_jacket_coats_id_en "夹克及大衣"
PostCategoryWithId $storeViewCN $women_coats_id_en "大衣"
PostCategoryWithId $storeViewCN $women_jackets_id_en "夹克"
PostCategoryWithId $storeViewCN $women_denim_jackets_id_en "牛仔夹克"
PostCategoryWithId $storeViewCN $women_leather_jackets_id_en "皮夹克"
PostCategoryWithId $storeViewCN $women_fur_shearling_id_en "皮草"
PostCategoryWithId $storeViewCN $women_down_id_en "羽绒服"
PostCategoryWithId $storeViewCN $women_blazers_id_en "西装外套"
PostCategoryWithId $storeViewCN $women_jacket_coats_id_en "风衣"
PostCategoryWithId $storeViewCN $women_bombers_id_en "飞行员夹克"
PostCategoryWithId $storeViewCN $women_vests_id_en "马甲背心"

PostCategoryWithId $storeViewCN $women_swimwear_id_en "泳装"
PostCategoryWithId $storeViewCN $women_bikinis_id_en "比基尼"
PostCategoryWithId $storeViewCN $women_one_piece_id_en "连体泳衣"

PostCategoryWithId $storeViewCN $women_jeans_id_en "牛仔裤"
PostCategoryWithId $storeViewCN $women_shorts_id_en "短裤"

PostCategoryWithId $storeViewCN $women_pants_id_en "裤装"
PostCategoryWithId $storeViewCN $women_leggings_id_en "打底裤"
PostCategoryWithId $storeViewCN $women_leather_pants_id_en "皮裤"
PostCategoryWithId $storeViewCN $women_sweatpants_id_en "运动裤"
PostCategoryWithId $storeViewCN $women_trousers_id_en "长裤"

PostCategoryWithId $storeViewCN $women_activewear_id_en "运动装"
PostCategoryWithId $storeViewCN $women_active_tops_id_en "上衣"
PostCategoryWithId $storeViewCN $women_hoodies_zipups_id_en "卫衣 &拉链衫"
PostCategoryWithId $storeViewCN $women_outerwear_id_en "外套"
PostCategoryWithId $storeViewCN $women_sport_shorts_skirts_id_en "短裤&半身裙"
PostCategoryWithId $storeViewCN $women_active_leggings_id_en "紧身裤"
PostCategoryWithId $storeViewCN $women_sports_bras_id_en "运动文胸"
PostCategoryWithId $storeViewCN $women_active_pants_id_en "运动裤"
PostCategoryWithId $storeViewCN $women_dresses_id_en "连衣裙"

PostCategoryWithId $storeViewCN $women_dresses_id_en "连衣裙"
PostCategoryWithId $storeViewCN $women_mid_length_dresses_id_en "中长款连衣裙"
PostCategoryWithId $storeViewCN $women_short_dresses_id_en "短款连衣裙"
PostCategoryWithId $storeViewCN $women_long_dressses_id_en "长款连衣裙"

PostCategoryWithId $storeViewCN $women_jumpsuits_id_en "连身裤"

PostCategoryWithId $storeViewCN $women_bag_accessories_id_en "包袋配饰"
PostCategoryWithId $storeViewCN $women_cosmetic_cases_id_en "化妆包"
PostCategoryWithId $storeViewCN $women_scarves_id_en "围巾"
PostCategoryWithId $storeViewCN $women_silks_cashmeres_en "丝绸及羊绒"
PostCategoryWithId $storeViewCN $women_fur_shearling_a_id_en "皮草"
PostCategoryWithId $storeViewCN $women_knits_id_en "针织衫"
PostCategoryWithId $storeViewCN $women_dog_accessories_id_en "宠物配饰"

PostCategoryWithId $storeViewCN $women_hats_id_en "帽子"
PostCategoryWithId $storeViewCN $women_headbands_hair_Accessories_id_en "头带及发饰"
PostCategoryWithId $storeViewCN $women_beanies_id_en "套头帽"
PostCategoryWithId $storeViewCN $women_caps_id_en "棒球帽"
PostCategoryWithId $storeViewCN $women_fedoras_panama_hats_id_en "爵士帽及巴拿马帽"
PostCategoryWithId $storeViewCN $women_beach_hats_id_en "遮阳帽"

PostCategoryWithId $storeViewCN $women_gloves_id_en "手套"
PostCategoryWithId $storeViewCN $women_watches_id_en "手表"
PostCategoryWithId $storeViewCN $women_towels_id_en "毛巾"

PostCategoryWithId $storeViewCN $women_jewelry_id_en "珠宝首饰"
PostCategoryWithId $storeViewCN $women_rings_id_en "戒指"
PostCategoryWithId $storeViewCN $women_bracelets_id_en "手链"
PostCategoryWithId $storeViewCN $women_earrings_id_en "耳饰"
PostCategoryWithId $storeViewCN $women_brooches_id_en "胸针"
PostCategoryWithId $storeViewCN $women_cufflinks_id_en "袖口"
PostCategoryWithId $storeViewCN $women_necklaces_id_en "项链"

PostCategoryWithId $storeViewCN $women_TECH_id_en "电子产品及配件"
PostCategoryWithId $storeViewCN $women_iphone_cases_id_en "Iphone保护套"

PostCategoryWithId $storeViewCN $women_eyewear_id_en "眼镜"
PostCategoryWithId $storeViewCN $women_sunglasses_id_en "太阳镜"
PostCategoryWithId $storeViewCN $women_glasses_id_en "眼镜"

PostCategoryWithId $storeViewCN $women_belts_suspenders_id_en "腰带及背带"
PostCategoryWithId $storeViewCN $women_socks_id_en "袜子"
PostCategoryWithId $storeViewCN $women_blankets_id_en "被毯"
PostCategoryWithId $storeViewCN $women_keychains_id_en "钥匙扣"

PostCategoryWithId $storeViewCN $women_wallets_card_holders_id_en "钱包及卡包"
PostCategoryWithId $storeViewCN $women_card_holders_id_en "卡包"
PostCategoryWithId $storeViewCN $women_passport_holders_id_en "护照套"
PostCategoryWithId $storeViewCN $women_wallets_id_en "钱包"
PostCategoryWithId $storeViewCN $women_coin_pouches_id_en "零钱包"

PostCategoryWithId $storeViewCN $women_umbrellas_id_en "雨伞"
PostCategoryWithId $storeViewCN $women_fine_jewelry_id_en "高级珠宝"
PostCategoryWithId $storeViewCN $women_fine_rings_id_en "戒指"
PostCategoryWithId $storeViewCN $women_fine_earrings_id_en "耳饰"

PostCategoryWithId $storeViewCN $women_sandals_id_en "凉鞋"
PostCategoryWithId $storeViewCN $women_flat_sandals_id_en "平底凉鞋"
PostCategoryWithId $storeViewCN $women_heeled_sandals_id_en "高跟凉鞋"

PostCategoryWithId $storeViewCN $women_flats_id_en "平底鞋"
PostCategoryWithId $storeViewCN $women_slippers_loafers_id_en "拖鞋及乐福鞋"
PostCategoryWithId $storeViewCN $women_lace_up_oxfords_id_en "系带鞋及牛津鞋"
PostCategoryWithId $storeViewCN $women_ballerina_flats_id_en "芭蕾鞋"
PostCategoryWithId $storeViewCN $women_espadrilles_id_en "草编鞋"

PostCategoryWithId $storeViewCN $women_sneakers_id_en "运动鞋"
PostCategoryWithId $storeViewCN $women_low_top_sneakers_id_en "低帮运动鞋"
PostCategoryWithId $storeViewCN $women_high_top_sneakers_id_en "高帮运动鞋"

PostCategoryWithId $storeViewCN $women_boots_id_en "靴子"
PostCategoryWithId $storeViewCN $women_mid_calf_boots_id_en "中筒靴"
PostCategoryWithId $storeViewCN $women_ankle_boots_id_en "踝靴"
PostCategoryWithId $storeViewCN $women_tall_boots_id_en "高筒靴"

PostCategoryWithId $storeViewCN $women_heels_id_en "高跟鞋"

