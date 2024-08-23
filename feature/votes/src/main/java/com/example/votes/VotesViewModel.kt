package com.example.votes

import androidx.lifecycle.ViewModel
import com.example.common.model.ResultModel
import com.example.domain.model.FullVoteModel
import com.example.domain.model.VoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VotesViewModel @Inject constructor(

): ViewModel() {

    private val _listVotes = MutableStateFlow<ResultModel<List<VoteModel>>>(ResultModel.none())
    val listVotes: StateFlow<ResultModel<List<VoteModel>>>
        get() = _listVotes

    private val _fullInfoVote = MutableStateFlow<ResultModel<FullVoteModel>>(ResultModel.none())
    val fullInfoVote: StateFlow<ResultModel<FullVoteModel>>
        get() = _fullInfoVote

    fun loadListVotes() {

    }

    fun loadFullInfoVote(id: Int) {

    }

    fun sendVote(id: Int, category: String, vote: String) {

    }

}