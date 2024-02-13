package com.umc.ttoklip.presentation.hometown.dialog

import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.FragmentTogetherBottomSheetDialogBinding
import com.umc.ttoklip.presentation.base.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TogetherBottomSheetDialogFragment(private val completeClick: (List<Int>) -> Unit) :
    BaseBottomSheetDialogFragment<FragmentTogetherBottomSheetDialogBinding>(R.layout.fragment_together_bottom_sheet_dialog) {
    override fun initObserver() {

    }

    override fun initView() {
        binding.sortChipG.isSelectionRequired = true
        binding.sortChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.sort1 -> {}
                R.id.sort2 -> {}
                R.id.sort3 -> {}
                else -> {}
            }
        }

        binding.durationChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.board1 -> {}

                R.id.board2 -> {}

                R.id.board3 -> {}

                R.id.board4 -> {}

                R.id.board5 -> {}

                R.id.board6 -> {}

                R.id.board7 -> {}

                else -> {}
            }
        }

        binding.requiredAmountyChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.amount1 -> {}
                R.id.amount2 -> {}
                R.id.amount3 -> {}
                R.id.amount4 -> {}
                R.id.amount5 -> {}
                else -> {}
            }
        }

        binding.maxMemberChipG.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.member1 -> {}
                R.id.member2 -> {}
                R.id.member3 -> {}
                R.id.member4 -> {}
                R.id.member5 -> {}
                R.id.member6 -> {}
                else -> {}
            }
        }

        binding.resetBtn.setOnClickListener {
            binding.sortChipG.clearCheck()
            binding.durationChipG.clearCheck()
            binding.requiredAmountyChipG.clearCheck()
            binding.maxMemberChipG.clearCheck()
        }

        binding.completeBtn.setOnClickListener {
            completeClick(getResult())
            dismiss()
        }
    }

    private fun getResult(): List<Int> {
        with(binding) {
            val sort = when (sortChipG.checkedChipId) {
                R.id.sort1 -> 1
                R.id.sort2 -> 2
                R.id.sort3 -> 3
                else -> {
                    0
                }
            }
            val duration = when (durationChipG.checkedChipId) {
                R.id.board1 -> 1
                R.id.board2 -> 2
                R.id.board3 -> 3
                R.id.board4 -> 4
                R.id.board5 -> 5
                R.id.board5 -> 6
                R.id.board5 -> 7
                else -> {
                    0
                }
            }
            val requiredAmount = when (requiredAmountyChipG.checkedChipId) {
                R.id.amount1 -> 1
                R.id.amount2 -> 2
                R.id.amount3 -> 3
                R.id.amount4 -> 4
                R.id.amount5 -> 5
                else -> {
                    0
                }
            }

            val maxMember = when (maxMemberChipG.checkedChipId) {
                R.id.member1 -> 1
                R.id.member2 -> 2
                R.id.member3 -> 3
                R.id.member4 -> 4
                R.id.member5 -> 5
                R.id.member6 -> 6
                else -> {
                    0
                }
            }

            return listOf(sort, duration, requiredAmount, maxMember)
        }
    }
}