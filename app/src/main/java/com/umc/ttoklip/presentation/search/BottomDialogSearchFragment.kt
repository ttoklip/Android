package com.umc.ttoklip.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentBottomDialogSearchBinding

class BottomDialogSearchFragment(private val completeClick: (List<Int>) -> Unit) :
    BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomDialogSearchBinding
    val resultList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomDialogSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle( // Background -> Transparent.
            STYLE_NORMAL,
            R.style.TransparentBottomSheetDialogFragment
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sortChipG.isSelectionRequired = true
        binding.sortChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.sort1 -> {}
                R.id.sort2 -> {}
                R.id.sort3 -> {}
                else -> {}
            }
        }

        binding.boardChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.board1 -> {
                    checkedNews()
                }

                R.id.board2 -> {
                    binding.categoryG.isGone = true
                }

                R.id.board3 -> {
                    checkedTip()
                }

                R.id.board4 -> {
                    binding.categoryG.isGone = true
                }

                R.id.board5 -> {
                    binding.categoryG.isGone = true
                }

                else -> {
                    binding.categoryG.isGone = true
                    binding.categoryChipG.clearCheck()
                }
            }
        }

        binding.categoryChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.category1 -> {}
                R.id.category2 -> {}
                R.id.category3 -> {}
                R.id.category4 -> {}
                R.id.category5 -> {}
                else -> {}
            }
        }

        binding.resetBtn.setOnClickListener {
            binding.sortChipG.check(R.id.sort1)
            binding.boardChipG.clearCheck()
            binding.categoryChipG.clearCheck()
        }

        binding.completeBtn.setOnClickListener {
            completeClick(getResult())
            dismiss()
        }
    }

    private fun checkedNews() {
        with(binding) {
            categoryChipG.clearCheck()
            categoryG.isVisible = true
            category1.text = "집안일"
            category2.text = "레시피"
            category3.text = "안전한 생활"
            category4.text = "복지·정책"
            category5.isGone = true
        }
    }

    private fun checkedTip() {
        with(binding) {
            categoryChipG.clearCheck()
            categoryG.isVisible = true
            category1.text = "집안일"
            category2.text = "요리"
            category3.text = "안전한 생활"
            category4.text = "사기"
            category5.text = "복지·정책"
            category5.isVisible = true
        }
    }

    private fun getResult() : List<Int>{
        with(binding){
            val sort = when (sortChipG.checkedChipId ){
                R.id.sort1 -> 1
                R.id.sort2 -> 2
                R.id.sort3 -> 3
                else -> {0}
            }
            val board = when (boardChipG.checkedChipId ){
                R.id.board1 -> 1
                R.id.board2 -> 2
                R.id.board3 -> 3
                R.id.board4 -> 4
                R.id.board5 -> 5
                else -> {0}
            }
            val category = when (boardChipG.checkedChipId ){
                R.id.category1 -> 1
                R.id.category2 -> 2
                R.id.category3 -> 3
                R.id.category4 -> 4
                R.id.category5 -> 5
                else -> {0}
            }

            return listOf(sort, board, category)
        }
    }
}