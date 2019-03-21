package com.testone.demo.utils;

public class CommonData {

    public String htmlString = "<html><head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"></head><div style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\";padding:3.0pt 0in 0in 0in'>\n" +
            "    <hr style='border:none;border-top:solid #E1E1E1 1.0pt'>\n" +
            "    <b>发件人：</b> &quot;张腾达&quot;&lt;zhangtengda0315@163.com&gt;<br>\n" +
            "    <b>发送时间：</b> 2019年03月18日 18时35分14秒<br>\n" +
            "    <b>收件人：</b> zhangtd@dehuinet.com<br>\n" +
            "    <b>主　题：</b> 这是一封测试邮件，没事<br>\n" +
            "    </div>\n" +
            "    <br>\n" +
            "    <div style=\"line-height:1.7;color:#000000;font-size:14px;font-family:Arial\"><div>&nbsp; &nbsp; &nbsp; &nbsp;1：这是测试邮件1</div><div>&nbsp; &nbsp; &nbsp; &nbsp;2：这是测试邮件2</div><div>&nbsp; &nbsp; &nbsp; &nbsp;2：这是测试邮件2</div><div><br></div><div>2：这是测试邮件2</div><div><br></div><div>2：这是<span style=\"color: rgb(255, 0, 0);\">测试邮件2</span></div><div><span style=\"color: rgb(255, 0, 0);\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span><img src=\"content://com.fsck.k9.mx.attachmentprovider/19bfe67f-cef1-49de-b710-9045752964f3/1/RAW\" orgwidth=\"700\" orgheight=\"464\" data-image=\"1\" style=\"width: 700px; height: 464px; border: 1px solid rgb(153, 153, 153);\"></div></div><br><br><span title=\"neteasefooter\"><p>&nbsp;</p></span></html>";

    public String backgroudHtmlString = "<html>\n" +
            "\t<head>\n" +
            "\t\t<style type=\"text/css\">\n" +
            "\n" +
            "\t\t\tbody {background-color: yellow}\n" +
            "\t\t\th1 {background-color: #00ff00}\n" +
            "\t\t\th2 {background-color: transparent}\n" +
            "\t\t\tp {background-color: rgb(250,0,255)}\n" +
            "\n" +
            "\t\t\tp.no2 {background-color: gray; padding: 20px;}\n" +
            "\n" +
            "\t\t</style>\n" +
            "\t\t<meta content=\\\"text/html; charset=utf-8\\\" http-equiv=\\\"Content-Type\\\">\n" +
            "\t</head>\n" +
            "\n" +
            "\t<h1>这是标题 1</h1>\n" +
            "<h2>这是标题 2</h2>\n" +
            "<p>这是段落</p>\n" +
            "<p class=\"no2\">这个段落设置了内边距。</p>\n" +
            "\n" +
            "\t<div style='font-size:10.0pt;font-family:\\\"Tahoma\\\",\\\"sans-serif\\\";padding:3.0pt 0in 0in 0in'>\n" +
            "\t\t<hr style='border:none;border-top:solid #E1E1E1 1.0pt'>\n" +
            "\t\t<b>发件人：</b> &quot;张腾达&quot;&lt;zhangtengda0315@163.com&gt;<br>\n" +
            "\t\t<b>发送时间：</b> 2019年03月18日 18时35分14秒<br>\n" +
            "\t\t<b>收件人：</b> zhangtd@dehuinet.com<br>\n" +
            "\t\t<b>主　题：</b> 这是一封测试邮件，没事<br><br>\n" +
            "        <div style=\\\"line-height:1.7;color:#000000;font-size:14px;font-family:Arial\\\"><div>&nbsp; &nbsp; &nbsp; &nbsp;1：这是测试邮件1</div><div>&nbsp; &nbsp; &nbsp; &nbsp;2：这是测试邮件2</div>\n" +
            "        <div>&nbsp; &nbsp; &nbsp; &nbsp;2：这是测试邮件2</div>\n" +
            "        <div><br></div><div>2：这是测试邮件2</div>\n" +
            "        <div><br></div><div>2：这是<span style=\\\"color: rgb(255, 0, 0);\\\">测试邮件2</span></div><div><span style=\\\"color: rgb(255, 0, 0);\\\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span>\n" +
            "        \t<img src=\\\"content://com.fsck.k9.mx.attachmentprovider/19bfe67f-cef1-49de-b710-9045752964f3/1/RAW\\\" orgwidth=\\\"700\\\" orgheight=\\\"464\\\" data-image=\\\"1\\\" style=\\\"width: 700px; height: 464px; border: 1px solid rgb(153, 153, 153);\\\"></div></div><br>\n" +
            "        \t<br>\n" +
            "\n" +
            "\n" +
            "\t<span title=\\\"neteasefooter\\\"><p>&nbsp;</p></span>\n" +
            "</html>";

    public String text1 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "body {color:#00ff00}\n" +
            "h1 {color:#00ff00}\n" +
            "p.ex {color:rgb(0,0,255)}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>这是 heading 1</h1>\n" +
            "<p>这是一段普通的段落。请注意，该段落的文本是红色的。在 body 选择器中定义了本页面中的默认文本颜色。</p>\n" +
            "<p class=\"ex\">该段落定义了 class=\"ex\"。该段落中的文本是蓝色的。</p>\n" +
            "</body>\n" +
            "</html>";

    public String text2 = "<html>\n" +
            "\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.small {line-height: 90%}\n" +
            "p.big {line-height: 200%}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<p>\n" +
            "这是拥有标准行高的段落。\n" +
            "在大多数浏览器中默认行高大约是 110% 到 120%。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "</p>\n" +
            "\n" +
            "<p class=\"small\">\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "</p>\n" +
            "\n" +
            "<p class=\"big\">\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n" +
            "\n";

    public String text3 = "<html>\n" +
            "\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "h1 {letter-spacing: -0.5em}\n" +
            "h4 {letter-spacing: 20px}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>This is header 1</h1>\n" +
            "<h4>This is header 4</h4>\n" +
            "</body>\n" +
            "\n" +
            "</html>";

    public String text4 = "<html>\n" +
            "\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.small\n" +
            "  {\n" +
            "  line-height: 10px\n" +
            "  }\n" +
            "p.big\n" +
            "  {\n" +
            "  line-height: 30px\n" +
            "  }\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<p>\n" +
            "这是拥有标准行高的段落。\n" +
            "在大多数浏览器中默认行高大约是 20px。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "这是拥有标准行高的段落。\n" +
            "</p>\n" +
            "\n" +
            "<p class=\"small\">\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "这个段落拥有更小的行高。\n" +
            "</p>\n" +
            "\n" +
            "<p class=\"big\">\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "这个段落拥有更大的行高。\n" +
            "</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    public String text5 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "h1 {text-align: center}\n" +
            "h2 {text-align: left}\n" +
            "h3 {text-align: right}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>这是标题 1</h1>\n" +
            "<h2>这是标题 2</h2>\n" +
            "<h3>这是标题 3</h3>\n" +
            "</body>\n" +
            "\n" +
            "</html>";

    public String text6 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "h1 {text-decoration: overline}\n" +
            "h2 {text-decoration: line-through}\n" +
            "h3 {text-decoration: underline}\n" +
            "h4 {text-decoration:blink}\n" +
            "a {text-decoration: none}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>这是标题 1</h1>\n" +
            "<h2>这是标题 2</h2>\n" +
            "<h3>这是标题 3</h3>\n" +
            "<h4>这是标题 4</h4>\n" +
            "<p><a href=\"http://www.w3school.com.cn/index.html\">这是一个链接</a></p>\n" +
            "</body>\n" +
            "\n" +
            "</html>";

    public String text7 = "<html>\n" +
            "\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "  h1 {text-transform: uppercase}\n" +
            "  p.uppercase {text-transform: uppercase}\n" +
            "  p.lowercase {text-transform: lowercase}\n" +
            "  p.capitalize {text-transform: capitalize}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>This Is An H1 Element</h1>\n" +
            "<p class=\"uppercase\">This is some text in a paragraph.</p>\n" +
            "<p class=\"lowercase\">This is some text in a paragraph.</p>\n" +
            "<p class=\"capitalize\">This is some text in a paragraph.</p>\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";

    public String text8 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p\n" +
            "{\n" +
            "white-space: nowrap\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<p>\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "这是一些文本。\n" +
            "</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    public String text9 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.spread {word-spacing: 30px;}\n" +
            "p.tight {word-spacing: -0.5em;}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p class=\"spread\">This is some text. This is some text.</p>\n" +
            "<p class=\"tight\">This is some text. This is some text.</p>\n" +
            "</body>\n" +
            "</html>";

    public String text10 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.serif{font-family:\"Times New Roman\",Georgia,Serif}\n" +
            "p.sansserif{font-family:Arial,Verdana,Sans-serif}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>CSS font-family</h1>\n" +
            "<p class=\"serif\">This is a paragraph, shown in the Times New Roman font.</p>\n" +
            "<p class=\"sansserif\">This is a paragraph, shown in the Arial font.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public String text11 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "h1 {font-size: 300%}\n" +
            "h2 {font-size: 200%}\n" +
            "p {font-size: 100%}\n" +
            "p.normal {font-style:normal}\n" +
            "p.italic {font-style:italic}\n" +
            "p.oblique {font-style:oblique}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h1>This is header 1</h1>\n" +
            "<h2>This is header 2</h2>\n" +
            "<p>This is a paragraph</p>\n" +
            "\n" +
            "<p class=\"normal\">This is a paragraph, normal.</p>\n" +
            "<p class=\"italic\">This is a paragraph, italic.</p>\n" +
            "<p class=\"oblique\">This is a paragraph, oblique.</p>\n" +
            "</body>\n" +
            "\n" +
            "</html>";

    public String text12 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.normal {font-variant: normal}\n" +
            "p.small {font-variant: small-caps}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p class=\"normal\">This is a paragraph</p>\n" +
            "<p class=\"small\">This is a paragraph</p>\n" +
            "</body>\n" +
            "\n" +
            "</html>";

    public String text13 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.ex1\n" +
            "{\n" +
            "font:italic arial,sans-serif;\n" +
            "}\n" +
            "\n" +
            "p.ex2\n" +
            "{\n" +
            "font:italic bold 12px/30px arial,sans-serif;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p class=\"ex1\">This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph.</p>\n" +
            "<p class=\"ex2\">This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph. This is a paragraph.</p>\n" +
            "</body>\n" +
            "</html>";

    public String text14 = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "a.one:link {color:#ff0000;}\n" +
            "a.one:visited {color:#0000ff;}\n" +
            "a.one:hover {color:#ffcc00;}\n" +
            "\n" +
            "a.two:link {color:#ff0000;}\n" +
            "a.two:visited {color:#0000ff;}\n" +
            "a.two:hover {font-size:150%;}\n" +
            "\n" +
            "a.three:link {color:#ff0000;}\n" +
            "a.three:visited {color:#0000ff;}\n" +
            "a.three:hover {background:#66ff66;}\n" +
            "\n" +
            "a.four:link {color:#ff0000;}\n" +
            "a.four:visited {color:#0000ff;}\n" +
            "a.four:hover {font-family:'微软雅黑';}\n" +
            "\n" +
            "a.five:link {color:#ff0000;text-decoration:none;}\n" +
            "a.five:visited {color:#0000ff;text-decoration:none;}\n" +
            "a.five:hover {text-decoration:underline;}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p>请把鼠标指针移动到下面的链接上，看看它们的样式变化。</p>\n" +
            "\n" +
            "<p><b><a class=\"one\" href=\"/index.html\" target=\"_blank\">这个链接改变颜色</a></b></p>\n" +
            "<p><b><a class=\"two\" href=\"/index.html\" target=\"_blank\">这个链接改变字体尺寸</a></b></p>\n" +
            "<p><b><a class=\"three\" href=\"/index.html\" target=\"_blank\">这个链接改变背景色</a></b></p>\n" +
            "<p><b><a class=\"four\" href=\"/index.html\" target=\"_blank\">这个链接改变字体</a></b></p>\n" +
            "<p><b><a class=\"five\" href=\"/index.html\" target=\"_blank\">这个链接改变文本的装饰</a></b></p>\n" +
            "</body>\n" +
            "\n" +
            "</html>";
    public String text15 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "ul.disc {list-style-type: disc}\n" +
            "ul.circle {list-style-type: circle}\n" +
            "ul.square {list-style-type: square}\n" +
            "ul.none {list-style-type: none}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<ul class=\"disc\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"circle\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"square\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"none\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";
    public String text16 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "ol.decimal {list-style-type: decimal}\n" +
            "ol.lroman {list-style-type: lower-roman}\n" +
            "ol.uroman {list-style-type: upper-roman}\n" +
            "ol.lalpha {list-style-type: lower-alpha}\n" +
            "ol.ualpha {list-style-type: upper-alpha}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<ol class=\"decimal\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ol>\n" +
            "\n" +
            "<ol class=\"lroman\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ol>\n" +
            "\n" +
            "<ol class=\"uroman\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ol>\n" +
            "\n" +
            "<ol class=\"lalpha\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ol>\n" +
            "\n" +
            "<ol class=\"ualpha\">\n" +
            "<li>咖啡</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ol>\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";
    public String text17 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "ul.none {list-style-type: none}\n" +
            "ul.disc {list-style-type: disc}\n" +
            "ul.circle {list-style-type: circle}\n" +
            "ul.square {list-style-type: square}\n" +
            "ul.decimal {list-style-type: decimal}\n" +
            "ul.decimal-leading-zero {list-style-type: decimal-leading-zero}\n" +
            "ul.lower-roman {list-style-type: lower-roman}\n" +
            "ul.upper-roman {list-style-type: upper-roman}\n" +
            "ul.lower-alpha {list-style-type: lower-alpha}\n" +
            "ul.upper-alpha {list-style-type: upper-alpha}\n" +
            "ul.lower-greek {list-style-type: lower-greek}\n" +
            "ul.lower-latin {list-style-type: lower-latin}\n" +
            "ul.upper-latin {list-style-type: upper-latin}\n" +
            "ul.hebrew {list-style-type: hebrew}\n" +
            "ul.armenian {list-style-type: armenian}\n" +
            "ul.georgian {list-style-type: georgian}\n" +
            "ul.cjk-ideographic {list-style-type: cjk-ideographic}\n" +
            "ul.hiragana {list-style-type: hiragana}\n" +
            "ul.katakana {list-style-type: katakana}\n" +
            "ul.hiragana-iroha {list-style-type: hiragana-iroha}\n" +
            "ul.katakana-iroha {list-style-type: katakana-iroha}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<ul class=\"none\">\n" +
            "<li>\"none\" 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"disc\">\n" +
            "<li>Disc 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"circle\">\n" +
            "<li>Circle 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"square\">\n" +
            "<li>Square 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"decimal\">\n" +
            "<li>Decimal 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"decimal-leading-zero\">\n" +
            "<li>Decimal-leading-zero 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"lower-roman\">\n" +
            "<li>Lower-roman 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"upper-roman\">\n" +
            "<li>Upper-roman 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"lower-alpha\">\n" +
            "<li>Lower-alpha 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"upper-alpha\">\n" +
            "<li>Upper-alpha 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"lower-greek\">\n" +
            "<li>Lower-greek 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"lower-latin\">\n" +
            "<li>Lower-latin 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"upper-latin\">\n" +
            "<li>Upper-latin 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"hebrew\">\n" +
            "<li>Hebrew 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"armenian\">\n" +
            "<li>Armenian 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"georgian\">\n" +
            "<li>Georgian 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"cjk-ideographic\">\n" +
            "<li>Cjk-ideographic 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"hiragana\">\n" +
            "<li>Hiragana 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"katakana\">\n" +
            "<li>Katakana 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"hiragana-iroha\">\n" +
            "<li>Hiragana-iroha 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "<ul class=\"katakana-iroha\">\n" +
            "<li>Katakana-iroha 类型</li>\n" +
            "<li>茶</li>\n" +
            "<li>可口可乐</li>\n" +
            "</ul>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
    public String text18 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "ul.inside \n" +
            "{\n" +
            "list-style-position: inside\n" +
            "}\n" +
            "\n" +
            "ul.outside \n" +
            "{\n" +
            "list-style-position: outside\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p>该列表的 list-style-position 的值是 \"inside\"：</p>\n" +
            "<ul class=\"inside\">\n" +
            "<li>Earl Grey Tea - 一种黑颜色的茶</li>\n" +
            "<li>Jasmine Tea - 一种神奇的“全功能”茶</li>\n" +
            "<li>Honeybush Tea - 一种令人愉快的果味茶</li>\n" +
            "</ul>\n" +
            "\n" +
            "<p>该列表的 list-style-position 的值是 \"outside\"：</p>\n" +
            "<ul class=\"outside\">\n" +
            "<li>Earl Grey Tea - 一种黑颜色的茶</li>\n" +
            "<li>Jasmine Tea - 一种神奇的“全功能”茶</li>\n" +
            "<li>Honeybush Tea - 一种令人愉快的果味茶</li>\n" +
            "</ul>\n" +
            "</body>\n" +
            "</html>\n";
    public String text19 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "#customers\n" +
            "  {\n" +
            "  font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
            "  width:100%;\n" +
            "  border-collapse:collapse;\n" +
            "  }\n" +
            "\n" +
            "#customers td, #customers th \n" +
            "  {\n" +
            "  font-size:1em;\n" +
            "  border:1px solid #98bf21;\n" +
            "  padding:3px 7px 2px 7px;\n" +
            "  }\n" +
            "\n" +
            "#customers th \n" +
            "  {\n" +
            "  font-size:1.1em;\n" +
            "  text-align:left;\n" +
            "  padding-top:5px;\n" +
            "  padding-bottom:4px;\n" +
            "  background-color:#A7C942;\n" +
            "  color:#ffffff;\n" +
            "  }\n" +
            "\n" +
            "#customers tr.alt td \n" +
            "  {\n" +
            "  color:#000000;\n" +
            "  background-color:#EAF2D3;\n" +
            "  }\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<table id=\"customers\">\n" +
            "<tr>\n" +
            "<th>Company</th>\n" +
            "<th>Contact</th>\n" +
            "<th>Country</th>\n" +
            "</tr>\n" +
            "\n" +
            "<tr>\n" +
            "<td>Apple</td>\n" +
            "<td>Steven Jobs</td>\n" +
            "<td>USA</td>\n" +
            "</tr>\n" +
            "\n" +
            "<tr class=\"alt\">\n" +
            "<td>Baidu</td>\n" +
            "<td>Li YanHong</td>\n" +
            "<td>China</td>\n" +
            "</tr>\n" +
            "\n" +
            "<tr>\n" +
            "<td>Google</td>\n" +
            "<td>Larry Page</td>\n" +
            "<td>USA</td>\n" +
            "</tr>\n" +
            "\n" +
            "<tr class=\"alt\">\n" +
            "<td>Lenovo</td>\n" +
            "<td>Liu Chuanzhi</td>\n" +
            "<td>China</td>\n" +
            "</tr>\n" +
            "\n" +
            "<tr>\n" +
            "<td>Microsoft</td>\n" +
            "<td>Bill Gates</td>\n" +
            "<td>USA</td>\n" +
            "</tr>\n" +
            "\n" +
            "<tr class=\"alt\">\n" +
            "<td>Nokia</td>\n" +
            "<td>Stephen Elop</td>\n" +
            "<td>Finland</td>\n" +
            "</tr>\n" +
            "\n" +
            "\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>\n";
    public String text20 = "<!DOCTYPE html>\n" +
            "\n" +
            "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "table\n" +
            "{\n" +
            "border-collapse: separate;\n" +
            "empty-cells: hide;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<table border=\"1\">\n" +
            "<tr>\n" +
            "<td>Adams</td>\n" +
            "<td>John</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>Bush</td>\n" +
            "<td></td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "\n" +
            "<p><b>注释：</b>如果已规定 !DOCTYPE，那么 Internet Explorer 8 （以及更高版本）支持 empty-cells 属性。</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
    public String text21 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "\n" +
            "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "table.one \n" +
            "{\n" +
            "border-collapse: separate;\n" +
            "border-spacing: 10px\n" +
            "}\n" +
            "table.two\n" +
            "{\n" +
            "border-collapse: separate;\n" +
            "border-spacing: 10px 50px\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<table class=\"one\" border=\"1\">\n" +
            "<tr>\n" +
            "<td>Adams</td>\n" +
            "<td>John</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>Bush</td>\n" +
            "<td>George</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "\n" +
            "<br />\n" +
            "\n" +
            "<table class=\"two\" border=\"1\">\n" +
            "<tr>\n" +
            "<td>Carter</td>\n" +
            "<td>Thomas</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>Gates</td>\n" +
            "<td>Bill</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "\n" +
            "<p><b>注释：</b>如果已规定 !DOCTYPE，那么 Internet Explorer 8 （以及更高版本）支持 border-spacing 属性。</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
    public String text22 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "\n" +
            "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "caption\n" +
            "{\n" +
            "caption-side:bottom\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<table border=\"1\">\n" +
            "<caption>This is a caption</caption>\n" +
            "<tr>\n" +
            "<td>Adams</td>\n" +
            "<td>John</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>Bush</td>\n" +
            "<td>George</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "\n" +
            "<p><b>注释：</b>如果已规定 !DOCTYPE，那么 Internet Explorer 8 （以及更高版本）支持 caption-side 属性。</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";
//    在元素周围画线
    public String text23 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p \n" +
            "{\n" +
            "border:red solid thin;\n" +
            "outline:#00ff00 dotted thick;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p><b>注释：</b>只有在规定了 !DOCTYPE 时，Internet Explorer 8 （以及更高版本） 才支持 outline 属性。</p>\n" +
            "</body>\n" +
            "</html>\n";
//    设置轮廓的颜色
    public String text24 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "<html>\n" +
        "<head>\n" +
        "<style type=\"text/css\">\n" +
        "p \n" +
        "{\n" +
        "border:red solid thin;\n" +
        "outline-style:dotted;\n" +
        "outline-color:#00ff00;\n" +
        "}\n" +
        "</style>\n" +
        "</head>\n" +
        "\n" +
        "<body>\n" +
        "<p><b>注释：</b>只有在规定了 !DOCTYPE 时，Internet Explorer 8 （以及更高版本） 才支持 outline-color 属性。</p>\n" +
        "</body>\n" +
        "</html>\n";

//    设置轮廓的样式
    public String text25 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
        "<html>\n" +
        "<head>\n" +
        "<style type=\"text/css\">\n" +
        "p\n" +
        "{\n" +
        "border: red solid thin;\n" +
        "}\n" +
        "p.dotted {outline-style: dotted}\n" +
        "p.dashed {outline-style: dashed}\n" +
        "p.solid {outline-style: solid}\n" +
        "p.double {outline-style: double}\n" +
        "p.groove {outline-style: groove}\n" +
        "p.ridge {outline-style: ridge}\n" +
        "p.inset {outline-style: inset}\n" +
        "p.outset {outline-style: outset}\n" +
        "</style>\n" +
        "</head>\n" +
        "<body>\n" +
        "\n" +
        "<p class=\"dotted\">A dotted outline</p>\n" +
        "<p class=\"dashed\">A dashed outline</p>\n" +
        "<p class=\"solid\">A solid outline</p>\n" +
        "<p class=\"double\">A double outline</p>\n" +
        "<p class=\"groove\">A groove outline</p>\n" +
        "<p class=\"ridge\">A ridge outline</p>\n" +
        "<p class=\"inset\">An inset outline</p>\n" +
        "<p class=\"outset\">An outset outline</p>\n" +
        "\n" +
        "<p><b>注释：</b>只有在规定了 !DOCTYPE 时，Internet Explorer 8 （以及更高版本） 才支持 outline-style 属性。</p>\n" +
        "</body>\n" +
        "</html>\n";

//    设置轮廓的宽度
    public String text26 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.one\n" +
            "{\n" +
            "border:red solid thin;\n" +
            "outline-style:solid;\n" +
            "outline-width:thin;\n" +
            "}\n" +
            "p.two\n" +
            "{\n" +
            "border:red solid thin;\n" +
            "outline-style:dotted;\n" +
            "outline-width:3px;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<p class=\"one\">This is some text in a paragraph.</p>\n" +
            "<p class=\"two\">This is some text in a paragraph.</p>\n" +
            "\n" +
            "<p><b>注释：</b>只有在规定了 !DOCTYPE 时，Internet Explorer 8 （以及更高版本） 才支持 outline-width 属性。</p>\n" +
            "</body>\n" +
            "</html>\n";
//    内边距属性
    public String text27 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "td.test1 {padding: 1.5cm}\n" +
            "td.test2 {padding: 0.5cm 2.5cm}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<table border=\"1\">\n" +
            "<tr>\n" +
            "<td class=\"test1\">\n" +
            "这个表格单元的每个边拥有相等的内边距。\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "<br />\n" +
            "<table border=\"1\">\n" +
            "<tr>\n" +
            "<td class=\"test2\">\n" +
            "这个表格单元的上和下内边距是 0.5cm，左和右内边距是 2.5cm。\n" +
            "</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";
    public String text28 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.one \n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-width: 5px\n" +
            "}\n" +
            "p.two \n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-width: thick\n" +
            "}\n" +
            "p.three\n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-width: 5px 10px\n" +
            "}\n" +
            "p.four \n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-width: 5px 10px 1px\n" +
            "}\n" +
            "p.five \n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-width: 5px 10px 1px medium\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<p class=\"one\">Some text</p>\n" +
            "<p class=\"two\">Some text</p>\n" +
            "<p class=\"three\">Some text</p>\n" +
            "<p class=\"four\">Some text</p>\n" +
            "<p class=\"five\">Some text</p>\n" +
            "\n" +
            "<p><b>注释：</b>\"border-width\" 属性如果单独使用的话是不会起作用的。请首先使用 \"border-style\" 属性来设置边框。</p>\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";

    public String text29 = "<html>\n" +
            "<head>\n" +
            "\n" +
            "<style type=\"text/css\">\n" +
            "p.one\n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-color: #0000ff\n" +
            "}\n" +
            "p.two\n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-color: #ff0000 #0000ff\n" +
            "}\n" +
            "p.three\n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-color: #ff0000 #00ff00 #0000ff\n" +
            "}\n" +
            "p.four\n" +
            "{\n" +
            "border-style: solid;\n" +
            "border-color: #ff0000 #00ff00 #0000ff rgb(250,0,255)\n" +
            "}\n" +
            "</style>\n" +
            "\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<p class=\"one\">One-colored border!</p>\n" +
            "\n" +
            "<p class=\"two\">Two-colored border!</p>\n" +
            "\n" +
            "<p class=\"three\">Three-colored border!</p>\n" +
            "\n" +
            "<p class=\"four\">Four-colored border!</p>\n" +
            "\n" +
            "<p><b>注释：</b>\"border-width\" 属性如果单独使用的话是不会起作用的。请首先使用 \"border-style\" 属性来设置边框。</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    public String text30 = "<html>\n" +
            "<head>\n" +
            "<style type=\"text/css\">\n" +
            "p.margin {margin: 2cm 4cm 3cm 4cm}\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<p>这个段落没有指定外边距。</p>\n" +
            "\n" +
            "<p class=\"margin\">这个段落带有指定的外边距。这个段落带有指定的外边距。这个段落带有指定的外边距。这个段落带有指定的外边距。这个段落带有指定的外边距。</p>\n" +
            "\n" +
            "<p>这个段落没有指定外边距。</p>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>";
    public String text31 = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "ul\n" +
            "{\n" +
            "list-style-type:none;\n" +
            "margin:0;\n" +
            "padding:0;\n" +
            "padding-top:6px;\n" +
            "padding-bottom:6px;\n" +
            "}\n" +
            "li\n" +
            "{\n" +
            "display:inline;\n" +
            "}\n" +
            "a:link,a:visited\n" +
            "{\n" +
            "font-weight:bold;\n" +
            "color:#FFFFFF;\n" +
            "background-color:#98bf21;\n" +
            "text-align:center;\n" +
            "padding:6px;\n" +
            "text-decoration:none;\n" +
            "text-transform:uppercase;\n" +
            "}\n" +
            "a:hover,a:active\n" +
            "{\n" +
            "background-color:#7A991A;\n" +
            "}\n" +
            "\n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<ul>\n" +
            "<li><a href=\"#home\">Home</a></li>\n" +
            "<li><a href=\"#news\">News</a></li>\n" +
            "<li><a href=\"#contact\">Contact</a></li>\n" +
            "<li><a href=\"#about\">About</a></li>\n" +
            "</ul>\n" +
            "\n" +
            "<p><b>注释：</b>如果您只为 a 元素设置内边距（而不设置 ul 元素），那么链接会出现在 ul 元素之外。所以，我们为 ul 元素添加了 top 和 bottom 内边距。</p>\n" +
            "</body>\n" +
            "</html>\n";
    public String text32 = "";
    public String text33 = "";
    public String text34 = "";
    public String text35 = "";
    public String text36 = "";
    public String text37 = "";
    public String text38 = "";
    public String text39 = "";
    public String text40 = "";
    public String text41 = "";
    public String text42 = "";
    public String text43 = "";
    public String text44 = "";
    public String text45 = "";
    public String text46 = "";
    public String text47 = "";

}
