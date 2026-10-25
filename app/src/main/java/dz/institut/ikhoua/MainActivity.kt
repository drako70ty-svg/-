
package dz.institut.ikhoua

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private val milk = Color.rgb(248, 247, 242)
    private val cardColor = Color.rgb(255, 255, 252)
    private val ink = Color.rgb(43, 58, 72)
    private val blue = Color.rgb(83, 111, 137)
    private val muted = Color.rgb(125, 126, 120)
    private val gold = Color.rgb(190, 158, 103)
    private val border = Color.rgb(232, 226, 213)

    private lateinit var content: LinearLayout
    private lateinit var search: EditText

    private val semesters = listOf(
        "الفصل الأول",
        "الفصل الثاني",
        "الفصل الثالث",
        "الفصل الرابع",
        "الفصل الخامس"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = milk
        window.navigationBarColor = milk
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        showHome()
    }

    private fun showHome() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(milk)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        val scroll = ScrollView(this).apply {
            isFillViewport = true
            clipToPadding = false
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(18), dp(20), dp(24))
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        // رأس التطبيق
        val brand = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        val emblem = TextView(this).apply {
            text = "✦"
            textSize = 30f
            gravity = Gravity.CENTER
            setTextColor(gold)
            background = rounded(blue, 18)
        }

        brand.addView(
            emblem,
            LinearLayout.LayoutParams(dp(58), dp(58))
        )

        val brandText = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), 0, 0, 0)
        }

        val instituteTitle = text(
            "المعهد الوطني المتخصص في التكوين المهني الإخوة شتوح برج بوعريريج",
            16f,
            ink,
            true
        )
        instituteTitle.maxLines = 3
        instituteTitle.setLineSpacing(dp(2).toFloat(), 1f)

        brandText.addView(instituteTitle)
        brandText.addView(
            text("منصتك التعليمية الرقمية", 13f, muted)
        )

        brand.addView(
            brandText,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        content.addView(brand)
        space(content, 26)

        // بطاقة الترحيب
        val welcome = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(22), dp(22), dp(22))
            background = rounded(blue, 24)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        welcome.addView(
            text("مرحبًا بك 👋", 24f, Color.WHITE, true)
        )
        space(welcome, 8)
        welcome.addView(
            text(
                "كل دروسك وملخصاتك في مكان واحد. اختر تخصصك وفصلك للوصول إلى المواد التعليمية.",
                15f,
                Color.rgb(239, 242, 244)
            )
        )
        space(welcome, 16)

        val browseButton = Button(this).apply {
            text = "تصفح الدروس  ←"
            textSize = 14f
            setTextColor(ink)
            isAllCaps = false
            background = rounded(milk, 14)
            setOnClickListener {
                contentScrollToLessons()
            }
        }
        welcome.addView(
            browseButton,
            LinearLayout.LayoutParams(-2, dp(48))
        )

        content.addView(welcome)
        space(content, 24)

        // البحث
        sectionTitle(content, "ابحث عن درس", "اكتب اسم المادة أو الفصل")

        search = EditText(this).apply {
            hint = "مثال: Initiation en informatique"
            textSize = 14f
            singleLine = true
            setTextColor(ink)
            setHintTextColor(muted)
            setPadding(dp(16), 0, dp(16), 0)
            background = rounded(cardColor, 16, border)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        content.addView(
            search,
            LinearLayout.LayoutParams(-1, dp(54))
        )
        space(content, 10)

        val searchButton = Button(this).apply {
            text = "بحث"
            textSize = 14f
            isAllCaps = false
            setTextColor(Color.WHITE)
            background = rounded(blue, 14)
            setOnClickListener {
                val query = search.text.toString().trim()
                if (query.isEmpty()) {
                    toast("اكتب اسم المادة أو الفصل أولًا")
                } else {
                    toast("سيتم تفعيل البحث في ملفات الدروس عند ربط قاعدة البيانات")
                }
            }
        }

        content.addView(
            searchButton,
            LinearLayout.LayoutParams(-1, dp(46))
        )
        space(content, 26)

        // التخصصات
        sectionTitle(content, "التخصصات", "اختر تخصصك للوصول إلى دروسه")

        addCard(
            content,
            "تقني سامٍ في الدعم التقني وصيانة الإعلام الآلي",
            "Technicien Supérieur Support Technique et Maintenance Informatique",
            "💻"
        ) {
            showSemesters()
        }

        space(content, 26)

        // الفصول
        content.addView(
            text("الفصول الدراسية", 20f, ink, true)
        )
        space(content, 5)
        content.addView(
            text("خمسة فصول للوصول المنظم إلى الدروس", 13f, muted)
        )
        space(content, 12)

        semesters.forEachIndexed { index, semester ->
            addCard(
                content,
                semester,
                "عرض المواد والدروس والملخصات",
                "${index + 1}"
            ) {
                showSemester(index)
            }
            space(content, 10)
        }

        space(content, 18)

        // مساحة الأساتذة
        sectionTitle(content, "فضاء الأساتذة", "للأساتذة المصرح لهم")

        addCard(
            content,
            "دخول الأساتذة",
            "إضافة الدروس وملفات PDF",
            "↑"
        ) {
            showTeacherNotice()
        }

        space(content, 10)

        addCard(
            content,
            "الإدارة",
            "إدارة الحسابات والمحتوى التعليمي",
            "⚙"
        ) {
            showAdminNotice()
        }

        space(content, 28)

        // التذييل
        val footer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(12), dp(18), dp(12), dp(12))
            background = rounded(Color.rgb(241, 238, 229), 18)
        }

        footer.addView(
            text("مبرمج التطبيق", 13f, muted, true)
        )
        space(footer, 5)
        footer.addView(
            text("عبد الجبار بن كماش", 17f, ink, true)
        )
        space(footer, 7)
        footer.addView(
            text("الإصدار 1.0", 12f, muted)
        )

        content.addView(footer)

        scroll.addView(content)
        root.addView(
            scroll,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )

        setContentView(root)
    }

    private fun showSemesters() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(24))
            setBackgroundColor(milk)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        root.addView(
            text("التخصص الدراسي", 25f, ink, true)
        )
        space(root, 8)
        root.addView(
            text(
                "تقني سامٍ في الدعم التقني وصيانة الإعلام الآلي",
                15f,
                muted
            )
        )
        space(root, 24)

        semesters.forEachIndexed { index, semester ->
            addCard(
                root,
                semester,
                "اضغط لعرض مواد الفصل",
                "${index + 1}"
            ) {
                showSemester(index)
            }
            space(root, 12)
        }

        val back = makeButton("العودة إلى الرئيسية", blue) {
            showHome()
        }
        root.addView(back)

        setContentView(root)
    }

    private fun showSemester(index: Int) {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(24))
            setBackgroundColor(milk)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        root.addView(
            text(semesters[index], 25f, ink, true)
        )
        space(root, 8)
        root.addView(
            text("المواد التعليمية وملفات PDF", 14f, muted)
        )
        space(root, 20)

        val info = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(20), dp(20), dp(20))
            background = rounded(cardColor, 20, border)
        }

        info.addView(
            text("📚  لا توجد دروس مضافة بعد", 18f, ink, true)
        )
        space(info, 10)
        info.addView(
            text(
                "ستظهر هنا المواد والملخصات والملفات التي يرفعها الأساتذة بعد إعداد نظام المحتوى.",
                14f,
                muted
            )
        )

        root.addView(info)
        space(root, 20)

        root.addView(
            makeButton("العودة إلى الفصول", blue) {
                showSemesters()
            }
        )
        space(root, 10)
        root.addView(
            makeButton("الرئيسية", Color.rgb(230, 226, 216)) {
                showHome()
            }.apply {
                setTextColor(ink)
            }
        )

        setContentView(root)
    }

    private fun showTeacherNotice() {
        showNoticePage(
            "فضاء الأساتذة",
            "تسجيل دخول الأساتذة",
            "سيُستخدم هذا القسم لتسجيل دخول الأستاذ ورفع ملفات PDF وتنظيمها حسب التخصص والفصل والمادة.",
            "تسجيل الدخول الآمن ورفع الملفات لم يُفعّلا بعد؛ يلزم إعداد قاعدة بيانات وتخزين للملفات."
        )
    }

    private fun showAdminNotice() {
        showNoticePage(
            "فضاء الإدارة",
            "إدارة التطبيق",
            "سيتيح هذا القسم إدارة حسابات الأساتذة وتنظيم المواد والفصول ومراجعة المحتوى المنشور.",
            "يجب إعداد صلاحيات الإدارة على الخادم حتى لا يستطيع أي مستخدم الوصول إليها."
        )
    }

    private fun showNoticePage(
        pageTitle: String,
        cardTitle: String,
        description: String,
        note: String
    ) {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(24))
            setBackgroundColor(milk)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        root.addView(text(pageTitle, 25f, ink, true))
        space(root, 20)

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(22), dp(20), dp(22))
            background = rounded(cardColor, 20, border)
        }

        card.addView(text(cardTitle, 20f, blue, true))
        space(card, 12)
        card.addView(text(description, 15f, ink))
        space(card, 16)
        card.addView(text(note, 13f, muted))

        root.addView(card)
        space(root, 24)

        root.addView(
            makeButton("العودة إلى الرئيسية", blue) {
                showHome()
            }
        )

        setContentView(root)
    }

    private fun sectionTitle(
        parent: LinearLayout,
        title: String,
        subtitle: String
    ) {
        parent.addView(text(title, 20f, ink, true))
        space(parent, 5)
        parent.addView(text(subtitle, 13f, muted))
        space(parent, 13)
    }

    private fun addCard(
        parent: LinearLayout,
        title: String,
        subtitle: String,
        symbol: String,
        action: () -> Unit
    ) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(14), dp(15), dp(14), dp(15))
            background = rounded(cardColor, 18, border)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            isClickable = true
            isFocusable = true
            setOnClickListener { action() }
        }

        val icon = TextView(this).apply {
            text = symbol
            textSize = if (symbol.length > 2) 21f else 18f
            gravity = Gravity.CENTER
            setTextColor(blue)
            background = rounded(Color.rgb(239, 237, 228), 14)
        }

        row.addView(
            icon,
            LinearLayout.LayoutParams(dp(48), dp(48))
        )

        val texts = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), 0, dp(8), 0)
        }

        texts.addView(text(title, 15f, ink, true))
        space(texts, 5)
        texts.addView(text(subtitle, 12f, muted))

        row.addView(
            texts,
            LinearLayout.LayoutParams(0, -2, 1f)
        )

        row.addView(text("‹", 27f, gold, true))

        parent.addView(
            row,
            LinearLayout.LayoutParams(-1, -2)
        )
    }

    private fun makeButton(
        label: String,
        color: Int,
        action: () -> Unit
    ): Button {
        return Button(this).apply {
            text = label
            textSize = 14f
            isAllCaps = false
            setTextColor(if (color == blue) Color.WHITE else ink)
            background = rounded(color, 14)
            setOnClickListener { action() }
        }
    }

    private fun text(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER_VERTICAL
            textDirection = View.TEXT_DIRECTION_RTL
            layoutDirection = View.LAYOUT_DIRECTION_RTL
        }
    }

    private fun rounded(
        color: Int,
        radius: Int,
        stroke: Int? = null
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
            if (stroke != null) {
                setStroke(dp(1), stroke)
            }
        }
    }

    private fun space(parent: LinearLayout, height: Int) {
        parent.addView(
            View(this),
            LinearLayout.LayoutParams(1, dp(height))
        )
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun contentScrollToLessons() {
        toast("اختر تخصصك أو أحد الفصول لعرض الدروس")
    }
}
