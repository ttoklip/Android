package com.umc.ttoklip.presentation.hometown.together.read

import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.umc.ttoklip.R
import com.umc.ttoklip.databinding.DialogParticipantsBinding
import com.umc.ttoklip.presentation.base.BaseDialogFragment
import com.umc.ttoklip.presentation.hometown.adapter.ParticipantsRVA
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ParticipantDialogFragment: BaseDialogFragment<DialogParticipantsBinding>(R.layout.dialog_participants) {
    private val viewModel: ReadTogetherViewModel by activityViewModels<ReadTogetherViewModelImpl>()
    private var _participantRVA: ParticipantsRVA? = null
    private val participantsRVA get() = _participantRVA
    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.participants.collect{
                    Log.d("part", it.toString())
                    binding.title.text = "현재 참여 인원: ${it.cartMemberResponses.size}명"
                    participantsRVA?.submitList(it.cartMemberResponses)
                }
            }
        }


    }

    override fun initView() {
        /*viewModel.fetchParticipantsCount()
        viewModel.fetchParticipants()*/
        initRVA()
        binding.acceptBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun initRVA(){
        _participantRVA = ParticipantsRVA()
        binding.rvParticipant.adapter = participantsRVA
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetParticipants()
        _participantRVA = null
    }
}