package com.testone.demo.activity.smiley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;

import com.testone.demo.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversationSmileyConversionUtil {

    private static final int PAGE_SIZE = 20;
    /*** 参照微信[300]，每次最多发送 300 个表情，剩下的以字符形式显示[如果表情太多，滑动页面时会很卡顿] ***/
    private static final int MAX_EMOJI_LIMIT = 300;

    private static ConversationSmileyConversionUtil mFaceConversionUtil;

    /**
     * 保存于内存中的表情HashMap
     */
    public Map<String, ConversationSmiley> emojiMap = new ArrayMap<>();

    /**
     * 保存于内存中的表情集合
     */
    private List<ConversationSmiley> emojis = new ArrayList<>();

    /**
     * 表情分页的结果集合
     */
    public List<List<ConversationSmiley>> emojiLists = new ArrayList<>();

    private static final String REGEX = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::lt|/::\\$|/::X|/::Z|/::\\.\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::hg|/:\\|-\\)|/::!|/::L|/::gt|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:and-\\(|/:B-\\)|/:lt@|/:@gt|/::-O|/:gt-\\||/:P-\\(|/::\\.\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:ltWgt|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:em_1:/|/:em_2:/|/:em_3:/|/:em_4:/|/:em_5:/|/:em_6:/|/:em_7:/|/:em_8:/|/:em_9:/|/:em_10:/|/:em_11:/|/:em_12:/|/:em_13:/|/:em_14:/|/:em_15:/|/:em_16:/|/:em_17:/|/:em_18:/|/:em_19:/|/:em_20:/|/:em_21:/|/:em_22:/|/:em_23:/|/:em_24:/|/:em_25:/|/:em_26:/|/:em_27:/|/:em_28:/|/:em_29:/|/:em_30:/|/:em_31:/|/:em_32:/|/:em_33:/|/:em_34:/|/:em_35:/|/:em_36:/|/:em_37:/|/:em_38:/|/:em_39:/|/:em_40:/|/:em_41:/|/:em_42:/|/:em_43:/|/:em_44:/|/:em_45:/|/:em_46:/|/:em_47:/|/:em_48:/|/:em_49:/|/:em_50:/|/:em_51:/|/:em_52:/|/:em_53:/|/:em_54:/|/:em_55:/|/:em_56:/|/:em_57:/|/:em_58:/|/:em_59:/|/:em_60:/|/:em_61:/|/:em_62:/|/:em_63:/|/:em_64:/|/:em_65:/|/:em_66:/|/:em_67:/|/:em_68:/|/:em_69:/|/:em_70:/|/:em_71:/|/:em_72:/|/:em_73:/|/:em_74:/|/:em_75:/|/:em_76:/|/:em_77:/|/:em_78:/|/:em_79:/|/:em_80:/|/:em_81:/|/:em_82:/|/:em_83:/|/:em_84:/|/:em_85:/|/:em_86:/|/:em_87:/|/:em_88:/|/:em_89:/|/:em_90:/|/:em_91:/|/:em_92:/|/:em_93:/|/:em_94:/|/:em_95:/|/:em_96:/|/:em_97:/|/:em_98:/|/:em_99:/|/:em_100:/|/:em_101:/|/:em_102:/|/:em_103:/|/:em_104:/|/:em_105:/|/:em_106:/|/:em_107:/|/:em_108:/|/:em_109:/|/:em_110:/|/:em_111:/|/:em_112:/|/:em_113:/|/:em_114:/|/:em_115:/|/:em_116:/|/:em_117:/|/:em_118:/|/:em_119:/|/:em_120:/|/:em_121:/|/:em_122:/|/:em_123:/|/:em_124:/|/:em_125:/|/:em_126:/|/:em_127:/|/:em_128:/|/:em_129:/|/:em_130:/|/:em_131:/|/:em_132:/|/:em_133:/|/:em_134:/|/:em_135:/|/:em_136:/|/:em_137:/|/:em_138:/|/:em_139:/|/:em_140:/|/:em_141:/|/:em_142:/|/:em_143:/|/:em_144:/|/:em_145:/|/:em_146:/|/:em_147:/|/:em_148:/|/:em_149:/|/:em_150:/|/:em_151:/|/:em_152:/|/:em_153:/|/:em_154:/|/:em_155:/|/:em_156:/|/:em_157:/|/:em_158:/|/:em_159:/|/:em_160:/";
// 	private static final String REGEX = "/:em_1:/|/:em_2:/|/:em_3:/|/:em_4:/|/:em_5:/|/:em_6:/|/:em_7:/|/:em_8:/|/:em_9:/|/:em_10:/|/:em_11:/|/:em_12:/|/:em_13:/|/:em_14:/|/:em_15:/|/:em_16:/|/:em_17:/|/:em_18:/|/:em_19:/|/:em_20:/|/:em_21:/|/:em_22:/|/:em_23:/|/:em_24:/|/:em_25:/|/:em_26:/|/:em_27:/|/:em_28:/|/:em_29:/|/:em_30:/|/:em_31:/|/:em_32:/|/:em_33:/|/:em_34:/|/:em_35:/|/:em_36:/|/:em_37:/|/:em_38:/|/:em_39:/|/:em_40:/|/:em_41:/|/:em_42:/|/:em_43:/|/:em_44:/|/:em_45:/|/:em_46:/|/:em_47:/|/:em_48:/|/:em_49:/|/:em_50:/|/:em_51:/|/:em_52:/|/:em_53:/|/:em_54:/|/:em_55:/|/:em_56:/|/:em_57:/|/:em_58:/|/:em_59:/|/:em_60:/|/:em_61:/|/:em_62:/|/:em_63:/|/:em_64:/|/:em_65:/|/:em_66:/|/:em_67:/|/:em_68:/|/:em_69:/|/:em_70:/|/:em_71:/|/:em_72:/|/:em_73:/|/:em_74:/|/:em_75:/|/:em_76:/|/:em_77:/|/:em_78:/|/:em_79:/|/:em_80:/|/:em_81:/|/:em_82:/|/:em_83:/|/:em_84:/|/:em_85:/|/:em_86:/|/:em_87:/|/:em_88:/|/:em_89:/|/:em_90:/|/:em_91:/|/:em_92:/|/:em_93:/|/:em_94:/|/:em_95:/|/:em_96:/|/:em_97:/|/:em_98:/|/:em_99:/|/:em_100:/|/:em_101:/|/:em_102:/|/:em_103:/|/:em_104:/|/:em_105:/|/:em_106:/|/:em_107:/|/:em_108:/|/:em_109:/|/:em_110:/|/:em_111:/|/:em_112:/|/:em_113:/|/:em_114:/|/:em_115:/|/:em_116:/|/:em_117:/|/:em_118:/|/:em_119:/|/:em_120:/|/:em_121:/|/:em_122:/|/:em_123:/|/:em_124:/|/:em_125:/|/:em_126:/|/:em_127:/|/:em_128:/|/:em_129:/|/:em_130:/|/:em_131:/|/:em_132:/|/:em_133:/|/:em_134:/|/:em_135:/|/:em_136:/|/:em_137:/|/:em_138:/|/:em_139:/|/:em_140:/|/:em_141:/|/:em_142:/|/:em_143:/|/:em_144:/|/:em_145:/|/:em_146:/|/:em_147:/|/:em_148:/|/:em_149:/|/:em_150:/|/:em_151:/|/:em_152:/|/:em_153:/|/:em_154:/|/:em_155:/|/:em_156:/|/:em_157:/|/:em_158:/|/:em_159:/|/:em_160:/";
//    private static final String REGEX = "/:em_[0-9]{1,3}:/";

    // 使用 RegexBuddy 修正之后的表情匹配正则
    // "/::[)|+]|/:{1,2}[\(|\)|\~|@|\$|\-|[0-9a-zA-Z]|\||!|\?|\\*|\\.|,]+"
//    private static final String REGEX = "/::[)|+]|/:{1,2}[\(|\)|\~|@|\$|\-|[0-9a-zA-Z]|\||!|\?|\\*|\\.|,]+";
    private static final Pattern PATTERN = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);

    private static final String[] SMILEY_KEYS_OLD = new String[]{"/::\\)", "/::~", "/::B", "/::\\|", "/:8-\\)", "/::lt", "/::\\$", "/::X", "/::Z",
            "/::\\.\\(", "/::-\\|", "/::@", "/::P", "/::D", "/::O", "/::\\(", "/::\\+", "/:--b", "/::Q", "/::T", "/:,@P", "/:,@-D", "/::d", "/:,@o",
            "/::hg", "/:\\|-\\)", "/::!", "/::L", "/::gt", "/::,@", "/:,@f", "/::-S", "/:\\?", "/:,@x", "/:,@@", "/::8", "/:,@!", "/:!!!", "/:xx",
            "/:bye", "/:wipe", "/:dig", "/:handclap", "/:and-\\(", "/:B-\\)", "/:lt@", "/:@gt", "/::-O", "/:gt-\\|", "/:P-\\(", "/::\\.\\|", "/:X-\\)",
            "/::\\*", "/:@x", "/:8*", "/:pd", "/:ltWgt", "/:beer", "/:basketb", "/:oo", "/:coffee", "/:eat", "/:pig", "/:rose", "/:fade",
            "/:showlove", "/:heart", "/:break", "/:cake", "/:li", "/:bome", "/:kn", "/:footb", "/:ladybug", "/:shit", "/:moon", "/:sun", "/:gift",
            "/:hug", "/:strong", "/:weak", "/:share", "/:v", "/:@\\)", "/:jj", "/:@@", "/:bad", "/:lvu", "/:no", "/:ok"
    };

    private static final String[] SMILEY_KEYS = new String[]{
            "/:em_1:/", "/:em_2:/", "/:em_3:/", "/:em_4:/", "/:em_5:/", "/:em_6:/", "/:em_7:/", "/:em_8:/", "/:em_9:/", "/:em_10:/", "/:em_11:/", "/:em_12:/", "/:em_13:/", "/:em_14:/", "/:em_15:/", "/:em_16:/", "/:em_17:/", "/:em_18:/", "/:em_19:/", "/:em_20:/",
            "/:em_21:/", "/:em_22:/", "/:em_23:/", "/:em_24:/", "/:em_25:/", "/:em_26:/", "/:em_27:/", "/:em_28:/", "/:em_29:/", "/:em_30:/", "/:em_31:/", "/:em_32:/", "/:em_33:/", "/:em_34:/", "/:em_35:/", "/:em_36:/", "/:em_37:/", "/:em_38:/", "/:em_39:/", "/:em_40:/",
            "/:em_41:/", "/:em_42:/", "/:em_43:/", "/:em_44:/", "/:em_45:/", "/:em_46:/", "/:em_47:/", "/:em_48:/", "/:em_49:/", "/:em_50:/", "/:em_51:/", "/:em_52:/", "/:em_53:/", "/:em_54:/", "/:em_55:/", "/:em_56:/", "/:em_57:/", "/:em_58:/", "/:em_59:/", "/:em_60:/",
            "/:em_61:/", "/:em_62:/", "/:em_63:/", "/:em_64:/", "/:em_65:/", "/:em_66:/", "/:em_67:/", "/:em_68:/", "/:em_69:/", "/:em_70:/", "/:em_71:/", "/:em_72:/", "/:em_73:/", "/:em_74:/", "/:em_75:/", "/:em_76:/", "/:em_77:/", "/:em_78:/", "/:em_79:/", "/:em_80:/",
            "/:em_81:/", "/:em_82:/", "/:em_83:/", "/:em_84:/", "/:em_85:/", "/:em_86:/", "/:em_87:/", "/:em_88:/", "/:em_89:/", "/:em_90:/", "/:em_91:/", "/:em_92:/", "/:em_93:/", "/:em_94:/", "/:em_95:/", "/:em_96:/", "/:em_97:/", "/:em_98:/", "/:em_99:/", "/:em_100:/",
            "/:em_101:/", "/:em_102:/", "/:em_103:/", "/:em_104:/", "/:em_105:/", "/:em_106:/", "/:em_107:/", "/:em_108:/", "/:em_109:/", "/:em_110:/", "/:em_111:/", "/:em_112:/", "/:em_113:/", "/:em_114:/", "/:em_115:/", "/:em_116:/", "/:em_117:/", "/:em_118:/", "/:em_119:/", "/:em_120:/",
            "/:em_121:/", "/:em_122:/", "/:em_123:/", "/:em_124:/", "/:em_125:/", "/:em_126:/", "/:em_127:/", "/:em_128:/", "/:em_129:/", "/:em_130:/", "/:em_131:/", "/:em_132:/", "/:em_133:/", "/:em_134:/", "/:em_135:/", "/:em_136:/", "/:em_137:/", "/:em_138:/", "/:em_139:/", "/:em_140:/",
            "/:em_141:/", "/:em_142:/", "/:em_143:/", "/:em_144:/", "/:em_145:/", "/:em_146:/", "/:em_147:/", "/:em_148:/", "/:em_149:/", "/:em_150:/", "/:em_151:/", "/:em_152:/", "/:em_153:/", "/:em_154:/", "/:em_155:/", "/:em_156:/", "/:em_157:/", "/:em_158:/", "/:em_159:/", "/:em_160:/"
    };

    private static final String[] SMILEY_TEXTS_OLD = new String[]{"[微笑]", "[撇嘴]", "[色]", "[发呆]", "[得意]", "[流泪]", "[害羞]", "[闭嘴]", "[睡]", "[大哭]", "[尴尬]",
            "[发怒]", "[调皮]", "[呲牙]", "[惊讶]", "[难过]", "[酷]", "[冷汗]", "[抓狂]", "[吐]", "[偷笑]", "[可爱]", "[白眼]", "[傲慢]", "[饥饿]", "[困]", "[惊恐]", "[流汗]",
            "[憨笑]", "[悠闲]", "[奋斗]", "[咒骂]", "[疑问]", "[嘘]", "[晕]", "[疯了]", "[衰]", "[骷髅]", "[敲打]", "[再见]", "[擦汗]", "[抠鼻]", "[鼓掌]", "[糗大了]", "[坏笑]",
            "[左哼哼]", "[右哼哼]", "[哈欠]", "[鄙视]", "[委屈]", "[快哭了]", "[阴险]", "[亲亲]", "[吓]", "[可怜]", "[菜刀]", "[西瓜]", "[啤酒]", "[篮球]", "[乒乓]", "[咖啡]", "[饭]",
            "[猪头]", "[玫瑰]", "[凋谢]", "[示爱]", "[爱心]", "[心碎]", "[蛋糕]", "[闪电]", "[炸弹]", "[刀]", "[足球]", "[瓢虫]", "[便便]", "[月亮]", "[太阳]", "[礼物]", "[拥抱]",
            "[强]", "[弱]", "[握手]", "[胜利]", "[抱拳]", "[勾引]", "[拳头]", "[差劲]", "[爱你]", "[NO]", "[OK]"

    };

    private static final String[] SMILEY_TEXTS = new String[]{"[哈哈大笑]", "[笑哭]", "[亲亲哟]", "[一脸嫌弃]", "[眨眼]", "[哭了]", "[开心]", "[挤眉毛]", "[哦耶]", "[捂脸]", "[滑稽]", "[机智]", "[嘿嘿哟]", "[邪恶]",
            "[不开心]", "[可爱]", "[笑容]", "[色眯眯]", "[眼冒金星]", "[纠结]", "[流汗了]", "[呵呵]", "[尬]", "[舔嘴嘴]", "[眯眯眼]", "[思考一下]", "[气气的]", "[好怕哟]", "[别说话]", "[哇]", "[困困的]", "[要崩溃了]", "[沮丧流汗]", "[害羞了]",
            "[我都睡了]", "[翻白眼]", "[唉唉]", "[调皮]", "[酷酷的]", "[惊讶]", "[流口水]", "[见钱眼开]", "[哇哦]", "[哭唧唧]", "[有点难过]",
            "[轻微难过]", "[巨难过]", "[伤心的很]", "[难受]", "[难过了]", "[愤怒了]", "[懵了]", "[出洋相]", "[难过流汗]", "[带口罩]", "[伤心欲绝]", "[发烧]", "[头炸了]", "[感冒]", "[天使]", "[恶魔]", "[青蛙]", "[彩虹小马]", "[蜗牛]",
            "[小幽灵]", "[外星人]", "[兔兔]", "[捂眼小猴]", "[捂耳小猴]", "[偷笑小猴]", "[猪猪]", "[便便]", "[奶牛]", "[旺财]", "[笑脸猫咪]", "[色猫咪]", "[震惊猫咪]", "[狐狸]", "[老虎]", "[狮子]", "[否定之男]", "[否定之女]", "[ok之男]", "[ok之女]",
            "[举手之男]", "[举手之女]", "[兔女郎]", "[捂脸男人]", "[捂脸女人]", "[僵尸美女]", "[僵尸帅哥]", "[无奈男人]", "[无奈女人]", "[兔基友]", "[一家四口]", "[一家三口]", "[男婴]", "[女婴]", "[女人]", "[男人]", "[双手赞成]", "[鼓掌]", "[握手]", "[真好]", "[不喜欢]", "[拳头]",
            "[拼搏]", "[左勾拳]", "[右勾拳]", "[十]", "[剪刀手]", "[LOVEU]", "[上面]", "[下面]", "[二头肌]", "[瓦肯人你好]", "[左边]", "[右边]", "[拜托]", "[好呀]", "[女侦探]", "[女医生]", "[女博士]", "[男博士]", "[女领导]", "[男领导]", "[女科学家]", "[男科学家]", "[女王]", "[国王]", "[女圣诞老人]",
            "[圣诞老人]", "[警察]", "[工人]", "[警卫]", "[女人鱼]", "[男人鱼]", "[女吸血鬼]", "[男吸血鬼]", "[天使娃娃]", "[黑色月亮]",
            "[月亮脸]", "[月亮]", "[星星]", "[群星]", "[晴天]", "[四叶草]", "[多云]", "[阴天]", "[下雨]", "[雷阵雨]", "[雪]", "[水珠]",
            "[雨伞]", "[破壳]", "[小鸡]", "[雪人]", "[火]", "[闪电]", "[圣诞树]"
    };

    private int mSize;

    private ConversationSmileyConversionUtil() {
    }

    public static ConversationSmileyConversionUtil getInstace(Context context) {
        if (mFaceConversionUtil == null) {
            mFaceConversionUtil = new ConversationSmileyConversionUtil();
            mFaceConversionUtil.initSmileyData(context.getApplicationContext());
        }
        return mFaceConversionUtil;
    }


    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     */
    public SpannableStringBuilder getExpressionStringByBuilder(Context context,
                                                               SpannableStringBuilder src,
                                                               float textSize) {
        try {
            dealExpressionByBuilder(context, src, PATTERN, textSize * 1.3f);
        } catch (Exception e) {
            Log.e("dealExpressionByBuilder", e.getMessage());
        }
        return src;
    }


    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     *
     * @param context
     * @param spannableString
     * @param scale
     * @param textSize
     * @return
     */
//	public SpannableString getExpressionString(Context context, SpannableString spannableString, float scale, float textSize) {
//		try {
//			float newSize = resizeEmojiSize(context, scale,textSize);
//			dealExpression(context, spannableString, PATTERN, newSize);
//		} catch (Exception e) {
//			Log.e("dealExpression", e.getMessage());
//		}
//		return spannableString;
//	}

    /**
     * 添加表情
     *
     * @param context
     * @param imgId
     * @param spannableString
     * @return
     */
    @SuppressWarnings("deprecation")
    public SpannableString addFace(Context context, int imgId, String spannableString) {
        if (TextUtils.isEmpty(spannableString)) {
            return null;
        }
        int size = context.getResources().getDimensionPixelSize(R.dimen.mx_conversation_message_smiley_input_size);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgId);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
        ImageSpan imageSpan = new ImageSpan(bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public int deleteLastSmiley(String str) {
        Matcher matcher = PATTERN.matcher(str);
        String smileyKey = null;
        while (matcher.find()) {
            smileyKey = matcher.group();
        }

        if (matcher.hitEnd()) {
            if (smileyKey != null && !"".equals(smileyKey)) {
                return str.lastIndexOf(smileyKey);
            }
        }
        return -1;
    }


    public void paste(Context context, EditText view, String pasteString, int maxLength) {
	    /*if (maxLength < 0) {
            maxLength = MXUIEngine.getInstance().getCircleManager().getMaxCircleWords();
        }*/
        if (maxLength <= 0) {
            maxLength = 5000;
        }
        Matcher matcher = PATTERN.matcher(pasteString);
        String smileyKey = null;
        int start = 0;
        int smiley_start = 0;
        while (matcher.find()) {
            smileyKey = matcher.group();
            smiley_start = matcher.start();
            if (smiley_start > start) {
                String pasteTextBefore = pasteString.substring(start, smiley_start);
                int index = view.getSelectionStart();
                view.getText().insert(index, pasteTextBefore);
                final int selection = index + pasteTextBefore.length();
                view.setSelection(selection > maxLength ? maxLength : selection);
            }
            ConversationSmiley smiley = emojiMap.get(smileyKey);
            int index = view.getSelectionStart();
            SpannableString pasteFace = addFace(context, smiley.getImageID(), smileyKey);
            if ((index + pasteFace.length()) > maxLength) {
                return;
            }
            view.getText().insert(index, pasteFace);
            final int selection = index + pasteFace.length();
            view.setSelection(selection > maxLength ? maxLength : selection);
            start = matcher.end();
            if (selection > maxLength) {
                return;
            }
        }

        int index = view.getSelectionStart();

        String pasteTextAfter = pasteString.substring(start);

        if (maxLength > 0 && index + pasteTextAfter.length() > maxLength) {
            pasteTextAfter = pasteTextAfter.substring(0, maxLength - view.getText().length());
        }
        view.getText().insert(index, pasteTextAfter);
        view.setSelection(index + pasteTextAfter.length());
    }


    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @param spannableString
     * @param patten
     * @throws Exception
     */
    private void dealExpression(Context context, SpannableString spannableString,
                                Pattern patten, float emojiSize) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        int count = 0;
        while (matcher.find()) {
            String key = matcher.group();
            ConversationSmiley smiley = emojiMap.get(key);
            if (smiley == null) {
                continue;
            }
            final int start = matcher.start();
            final int end = start + key.length();
            final Object what;
            if (count < MAX_EMOJI_LIMIT) {
                count++;
                Bitmap bitmap = smileyToBitmap(context, smiley, emojiSize);
                // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                what = new CenterAlignImageSpan(context, bitmap);
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                // 将该图片替换字符串中规定的位置中
                spannableString.setSpan(what, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                // 超出范围的就不管了
                break;
            }
        }
    }

    private Bitmap smileyToBitmap(@NonNull Context context, @NonNull ConversationSmiley smiley, float size) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), smiley.getImageID());
//        final int finalSize = (int) (mSize * scale);
        final int finalSize = (int) size;
        bitmap = Bitmap.createScaledBitmap(bitmap, finalSize, finalSize, true);
        return bitmap;
    }

    private Bitmap smileyToBitmap(@NonNull Context context, @NonNull ConversationSmiley smiley) {
        return smileyToBitmap(context, smiley, mSize);
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     *
     * @param context
     * @param spannableString
     * @param patten
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private void dealExpressionByBuilder(Context context, SpannableStringBuilder spannableString,
                                         Pattern patten, float smileSize) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
//			int start = matcher.start();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
//			if (matcher.start() < start) {
//				continue;
//			}
            ConversationSmiley smiley = emojiMap.get(key);
            if (smiley == null) {
                continue;
            }

            Bitmap bitmap = smileyToBitmap(context, smiley, smileSize);
            // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
//			ImageSpan imageSpan = new ImageSpan(bitmap);
            ImageSpan imageSpan = new CenterAlignImageSpan(context, bitmap);

            // 计算该图片名字的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            // 将该图片替换字符串中规定的位置中
            spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

//			if (end < spannableString.length()) {
//				// 如果整个字符串还未验证完，则继续。。
//				dealExpressionByBuilder(context, spannableString, patten, end);
//			}
        }
    }

    public String replaceSmileyCodeWithText(String orgText) {
        Matcher matcher = PATTERN.matcher(orgText);
        while (matcher.find()) {
            ConversationSmiley smiley = emojiMap.get(matcher.group());
            orgText = orgText.replaceAll(smiley.getReplaceKey(), smiley.getText());
        }
        return orgText;
    }

    public String replaceSmileyCodeWithTextIncludeBracket(String orgText) {
        Matcher matcher = PATTERN.matcher(orgText);
        while (matcher.find()) {
            ConversationSmiley smiley = emojiMap.get(matcher.group());
            orgText = orgText.replaceAll(smiley.getReplaceKey(), "[" + smiley.getText() + "]");
        }
        return orgText;
    }

    /**
     * 将带表情的文本替换为表情
     *
     * @param context
     * @param view
     * @param pasteString
     * @param maxLength
     */
    public void replaceSmileyCodeWithEmoji(Context context, EditText view, String pasteString, int maxLength) {
        paste(context, view, pasteString, maxLength);
    }

    public String getSmileText(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
        Matcher m = p.matcher(text);
        while (m.find()) {
            String smileText = m.group().substring(1, m.group().length() - 1);
            String smileKey = getSmileKeyBySmileText(smileText);
            sb.append(smileKey);
        }
        return sb.toString();
    }

    public static String getShowText(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
        Matcher m = p.matcher(text);
        while (m.find()) {
            sb.append("[");
            sb.append(m.group().substring(1, m.group().length() - 1));
            sb.append("]");
        }
        return sb.toString();
    }

    private void initSmileyData(Context context) {
        mSize = context.getResources().getDimensionPixelSize(R.dimen.mx_conversation_message_smiley_size);
        ConversationSmiley smiley = null;
        emojis.clear();
        emojiLists.clear();
        emojiMap.clear();
        try {
            for (int i = 0; i < SMILEY_KEYS.length; i++) {

                int resID = context.getResources().getIdentifier("em_" + (i + 1), "drawable", context.getPackageName());
                if (resID != 0) {
                    smiley = new ConversationSmiley();
                    smiley.setImageID(resID);
                    smiley.setIndex(i);

                    smiley.setKey(SMILEY_KEYS[i].replace("\\", ""));
                    smiley.setReplaceKey(SMILEY_KEYS[i]);
                    smiley.setText(SMILEY_TEXTS[i]);
                    emojiMap.put(SMILEY_KEYS[i].replace("\\", ""), smiley);
                }
            }

            for (int i = 0; i < SMILEY_KEYS_OLD.length; i++) {
                final String resName = "smiley_" + i;
                int resID = context.getResources().getIdentifier("smiley_" + i, "drawable", context.getPackageName());
                if (resID != 0) {
                    smiley = new ConversationSmiley();
                    smiley.setResName(resName);
                    smiley.setImageID(resID);
                    smiley.setIndex(i);
                    smiley.setKey(SMILEY_KEYS_OLD[i].replace("\\", ""));
                    smiley.setReplaceKey(SMILEY_KEYS_OLD[i]);
                    smiley.setText(SMILEY_TEXTS_OLD[i]);
                    emojis.add(smiley);
                    emojiMap.put(SMILEY_KEYS_OLD[i].replace("\\", ""), smiley);
                }

            }

            int pageCount = (int) Math.ceil((double) emojis.size() / 20);
            for (int i = 0; i < pageCount; i++) {
                emojiLists.add(getData(i));
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "error = " + e.getMessage(), e);
        }
    }

    /**
     * 获取分页数据
     *
     * @param page
     * @return
     */
    private List<ConversationSmiley> getData(int page) {
        int startIndex = page * PAGE_SIZE;
        int endIndex = startIndex + PAGE_SIZE;

        if (endIndex > emojis.size()) {
            endIndex = emojis.size();
        }
        List<ConversationSmiley> list = new ArrayList<ConversationSmiley>();
        list.addAll(emojis.subList(startIndex, endIndex));
        if (list.size() < PAGE_SIZE) {
            for (int i = list.size(); i < PAGE_SIZE; i++) {
                ConversationSmiley object = new ConversationSmiley();
                list.add(object);
            }
        }
        if (list.size() == PAGE_SIZE) {
            ConversationSmiley backBtn = new ConversationSmiley();
            backBtn.setImageID(R.drawable.mx_del_btn);
            backBtn.setKey("del_btn");
            list.add(backBtn);
        }
        return list;
    }

    /**
     * 判断一个文本中是否包含表情
     *
     * @param text 文本
     * @return true 表示包含， false 表示不包含
     */
    public boolean isContainSmiley(@NonNull CharSequence text) {
        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()) {
            String key = matcher.group();
            ConversationSmiley smiley = emojiMap.get(key);
            if (smiley != null) {
                return true;
            }
        }
        return false;
    }

    private List<ConversationSmiley> findSmiley(@NonNull CharSequence text) {
        Matcher matcher = PATTERN.matcher(text);
        ConversationSmiley smiley;
        // 使用 HashSet 过滤掉重复的元素
        final Set<ConversationSmiley> smileySet = new HashSet<>();
        while (matcher.find()) {
            String key = matcher.group();
            smiley = emojiMap.get(key);
            if (smiley != null) {
                smileySet.add(smiley);
            }
        }
        return new ArrayList<>(smileySet);
    }

//    public String convertRichText(@NonNull Context context, @NonNull CharSequence text) {
//        if (!isContainSmiley(text)) {
//            return text.toString();
//        } else {
//            final List<ConversationSmiley> smileyList = findSmiley(text);
//            for (final ConversationSmiley smiley : smileyList) {
//                // 将文本中的表情替换成 img 标签
//                text = text.toString().replace(smiley.getKey(), smileyToImgTag(context, smiley));
//            }
//            return text.toString();
//        }
//    }

    /**
     * 将表情转换 img 标签
     *
     * @param {@link Context}
     * @param
     * @return &lt;img src='data:image/jpeg;base64,base64_src_data'/&gt; 格式的 img 标签
     */
//    private String smileyToImgTag(@NonNull Context context, @NonNull ConversationSmiley smiley) {
//        final String base64Image = ImageUtil.bitmap2Base64String(
//                smileyToBitmap(context, smiley), Bitmap.CompressFormat.PNG);
//        return "<img src='data:image/jpeg;base64," + base64Image + "'/>";
//    }
    public static String getSmileKeyBySmileText(String smileText) {
        int index = -1;
        for (int i = 0; i < SMILEY_TEXTS.length; i++) {
            if (SMILEY_TEXTS[i].contains(smileText)) {
                index = i;
                break;
            }
        }
        return SMILEY_KEYS[index].replace("\\", "");
    }


    /**
     * 为了获取不是静态的数据
     *
     * @param context
     * @return
     */
    public Map<String, ConversationSmiley> getEmojiMap(Context context) {
        Map<String, ConversationSmiley> emojiMap = new ArrayMap<>();
        try {
            for (int i = 0; i < SMILEY_KEYS.length; i++) {
                final String resName = "smiley_" + (i + 1);
                int resID = context.getResources().getIdentifier("em_" + (i + 1), "drawable", context.getPackageName());
                if (resID != 0) {
                    ConversationSmiley smiley = new ConversationSmiley();
                    smiley.setImageID(resID);
                    smiley.setIndex(i);
                    smiley.setResName(resName);
                    smiley.setKey(SMILEY_KEYS[i]);
                    smiley.setReplaceKey(SMILEY_KEYS[i]);
                    smiley.setText(SMILEY_TEXTS[i]);
                    emojiMap.put(SMILEY_KEYS[i], smiley);
                }
            }
        } catch (Exception e) {
            return emojiMap;
        }
        return emojiMap;
    }

    public List<List<ConversationSmiley>> getSmileyList(Context context) {
        List<List<ConversationSmiley>> list = new ArrayList<>();
        int pageCount = (int) Math.ceil((double) emojis.size() / 20);

        List<ConversationSmiley> emojis = new ArrayList<>();
        for (int i = 0; i < SMILEY_KEYS.length; i++) {
            final String resName = "smiley_" + (i+1);
            int resID = context.getResources().getIdentifier("em_" + (i+1), "drawable", context.getPackageName());
            if (resID != 0) {
                ConversationSmiley smiley = new ConversationSmiley();
                smiley.setImageID(resID);
                smiley.setIndex(i);
                smiley.setResName(resName);
                smiley.setKey(SMILEY_KEYS[i].replace("\\", ""));
                smiley.setReplaceKey(SMILEY_KEYS[i]);
                smiley.setText(SMILEY_TEXTS[i]);
                emojis.add(smiley);
            }
        }
        for (int i = 0; i < pageCount; i++) {
            list.add(getLocalData(i, emojis));
        }
        return list;
    }

    private List<ConversationSmiley> getLocalData(int page, List<ConversationSmiley> emojis) {
        int startIndex = page * PAGE_SIZE;
        int endIndex = startIndex + PAGE_SIZE;

        if (endIndex > emojis.size()) {
            endIndex = emojis.size();
        }
        List<ConversationSmiley> list = new ArrayList<ConversationSmiley>();
        list.addAll(emojis.subList(startIndex, endIndex));
        if (list.size() < PAGE_SIZE) {
            for (int i = list.size(); i < PAGE_SIZE; i++) {
                ConversationSmiley object = new ConversationSmiley();
                list.add(object);
            }
        }
        if (list.size() == PAGE_SIZE) {
            ConversationSmiley backBtn = new ConversationSmiley();
            backBtn.setImageID(R.drawable.mx_del_btn);
            backBtn.setKey("del_btn");
            list.add(backBtn);
        }
        return list;
    }
}
