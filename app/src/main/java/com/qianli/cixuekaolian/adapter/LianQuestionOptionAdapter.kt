package com.qianli.cixuekaolian.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.LianItem
import com.qianli.cixuekaolian.beans.LianItemQuestion
import com.qianli.cixuekaolian.beans.LianQuestionOption

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class LianQuestionOptionAdapter(
    var question: LianItemQuestion,
    var optionsHolder: RecyclerView,
    var lianItem: LianItem,
    var questionsHolder: RecyclerView
) :
    BaseQuickAdapter<LianQuestionOption, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: LianQuestionOption) {
        var indicator = holder.getView<ImageView>(R.id.lian_item_result_icon)
        var itemOption = holder.getView<TextView>(R.id.lian_item_option_main)
        itemOption.text = item.optionMainText

        if (!lianItem.submitted) {
            indicator.visibility = View.GONE
            question.userSelectedOptions?.let {
                if (it.contains(item.id)) {
                    itemOption.setTextColor(Color.parseColor("#2ea5ef"))
                } else {
                    itemOption.setTextColor(Color.parseColor("#000000"))
                }
            }
        } else {
            question.userSelectedOptions?.let {

                if (it.contains(item.id)) {
                    if (item.correctOption) {
                        itemOption.setTextColor(Color.parseColor("#2ea5ef"))
                        indicator.setImageResource(R.drawable.icon_lian_result_tick)
                        indicator.visibility = View.VISIBLE
                    } else {
                        itemOption.setTextColor(Color.parseColor("#FF0000"))
                        indicator.setImageResource(R.drawable.icon_lian_result_cross)
                        indicator.visibility = View.VISIBLE
                    }
                } else {
                    if (item.correctOption) {
                        itemOption.setTextColor(Color.parseColor("#2ea5ef"))
                        indicator.setImageResource(R.drawable.icon_lian_result_tick)
                        indicator.visibility = View.VISIBLE
                    } else {
                        indicator.visibility = View.GONE
                        itemOption.setTextColor(Color.parseColor("#333333"))
                    }
                }

            }
        }

        var itemAnswer = holder.getView<TextView>(R.id.lian_item_answer_main)
        itemAnswer.visibility = View.GONE


        var bookItemHolder = holder.getView<LinearLayout>(R.id.lian_item_option_holder)
        bookItemHolder.setTag(R.id.lian_item_option_holder, item)
        bookItemHolder.setOnClickListener {
            lianItem.submitted = false
            question.userSelectedOptions =
                mutableSetOf((it.getTag(R.id.lian_item_option_holder) as LianQuestionOption).id)
            optionsHolder.adapter?.notifyDataSetChanged()
        }
    }

}