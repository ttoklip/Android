package com.umc.ttoklip.presentation.mypage

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.honeytip.HoneyTipMain
import com.umc.ttoklip.databinding.ActivityMyHoneyTipBinding
import com.umc.ttoklip.presentation.base.BaseActivity
import com.umc.ttoklip.presentation.hometown.ReadCommunicationActivity
import com.umc.ttoklip.presentation.honeytip.adapter.HoneyTipListRVA
import com.umc.ttoklip.presentation.honeytip.adapter.OnItemClickListener
import com.umc.ttoklip.presentation.mypage.vm.MyHoneyTipViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyHoneyTipActivity : BaseActivity<ActivityMyHoneyTipBinding>(R.layout.activity_my_honey_tip),
    OnItemClickListener {
    private val viewModel: MyHoneyTipViewModel by viewModels<MyHoneyTipViewModel>()
    private val adapter by lazy {
        HoneyTipListRVA(this)
    }

    override fun initView() {
        val sortFilters = listOf(
            "꿀팁 공유",
            "질문해요",
            "소통해요"
        )
        binding.honeyTipFilterSpinner.adapter =
            SortSpinnerAdapter(this, sortFilters)
        binding.honeyTipFilterSpinner.setSelection(0)

        /*val honeyTipList = listOf(
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
            HoneyTips("똑똑이", "음식물 쓰레기 냄새 방지!!", "집에 가끔씩이지만 나타나는 바퀴벌레, 잘못 처리하면 알깐다고도...", "1일전", 0),
        )*/

        binding.myHoneyTipRv.layoutManager = LinearLayoutManager(this)
        binding.myHoneyTipRv.adapter = adapter
        binding.myHoneyTipRv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.honeyTipFilterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    viewModel.reset()

                    when (binding.honeyTipFilterSpinner.selectedItem.toString()) {
                        "소통해요" -> {
                            viewModel.getMyComms()
                        }

                        "꿀팁 공유" -> {
                            viewModel.getMyHoneyTips()
                        }

                        "질문해요" -> {
                            viewModel.getMyQuestions()
                        }

                        else -> {}
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        binding.myHoneyTipBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun initObserver() {
        with(lifecycleScope) {
            launch {
                viewModel.honeyTipList.collect {
                    adapter.submitList(it.map { tips ->
                        HoneyTipMain(
                            tips.id,
                            tips.title,
                            tips.content,
                            tips.writer,
                            tips.likeCount,
                            tips.commentCount,
                            tips.scrapCount,
                            ""
                        )
                    })
                }
            }

            launch {
                viewModel.commsList.collect {
                    adapter.submitList(it.map { comms ->
                        HoneyTipMain(
                            comms.id,
                            comms.title,
                            comms.content,
                            comms.writer,
                            comms.likeCount,
                            comms.commentCount,
                            comms.scrapCount,
                            ""
                        )
                    })
                }
            }

            launch {
                viewModel.questionList.collect {
                    adapter.submitList(it.map { question ->
                        HoneyTipMain(
                            question.id,
                            question.title,
                            question.content,
                            question.writer,
                            0,
                            question.commentCount,
                            0,
                            ""
                        )
                    })
                }
            }

        }
    }


    /*override fun onClick(honeyTips: HoneyTips) {
        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
    }*/

    override fun onClick(honeyTip: HoneyTipMain) {
        when (binding.honeyTipFilterSpinner.selectedItem.toString()) {
            "소통해요" -> {
                val intent = Intent(this, ReadCommunicationActivity::class.java)
                intent.putExtra("postId", honeyTip.id)
                startActivity(intent)
            }

            "꿀팁 공유" -> {
//                val intent = Intent(this, HoneyTipActivity::class.java)
//                intent.putExtra("postId", honeyTip.id)
//                startActivity(intent)
            }

            "질문해요" -> {
                /*
                val intent = Intent(this, HoneyTips::class.java)
                intent.putExtra("postId", honeyTip.id)
                startActivity(intent)

                 */
            }

            else -> {}
        }
    }
}