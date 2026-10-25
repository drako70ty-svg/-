
package dz.institut.ikhoua

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ScrollView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(18, 52, 88)
        window.navigationBarColor = Color.rgb(18, 52, 88)

        val scroll = ScrollView(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(28, 36, 28, 28)
        layout.setBackgroundColor(Color.rgb(245, 248, 252))

        val title = TextView(this)
        title.text = "المعهد الوطني الإخوة شتوح"
        title.textSize = 25f
        title.setTextColor(Color.rgb(18, 52, 88))
        title.gravity = Gravity.CENTER
        title.setPadding(8, 24, 8, 24)
        layout.addView(title)

        val subtitle = TextView(this)
        subtitle.text = "منصتك للدروس والمراجعة"
        subtitle.textSize = 17f
        subtitle.gravity = Gravity.CENTER
        subtitle.setTextColor(Color.DKGRAY)
        subtitle.setPadding(8, 8, 8, 28)
        layout.addView(subtitle)

        val sections = listOf(
            "📚  تصفح الدروس",
            "💻  تخصص الإعلام الآلي",
            "📅  السداسيات",
            "📄  ملفات PDF",
            "🔐  دخول الأساتذة"
        )

        for (section in sections) {
            val item = TextView(this)
            item.text = section
            item.textSize = 19f
            item.setTextColor(Color.rgb(18, 52, 88))
            item.gravity = Gravity.CENTER_VERTICAL
            item.textDirection = TextView.TEXT_DIRECTION_RTL
            item.setPadding(22, 24, 22, 24)
            item.setBackgroundColor(Color.WHITE)

            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 8, 0, 8)
            layout.addView(item, params)
        }

        scroll.addView(layout)
        setContentView(scroll)
    }
}
