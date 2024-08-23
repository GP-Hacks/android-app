package com.example.votes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.ResultModel
import com.example.domain.model.FullInfoVoteModel
import com.example.domain.model.VoteModel
import com.example.domain.usecase.GetFullInfoVoteByIdUseCase
import com.example.domain.usecase.GetVotesUseCase
import com.example.domain.usecase.SendVoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VotesViewModel @Inject constructor(
    private val getVotesUseCase: GetVotesUseCase,
    private val getFullInfoVoteByIdUseCase: GetFullInfoVoteByIdUseCase,
    private val sendVoteUseCase: SendVoteUseCase
): ViewModel() {

    private val _listVotes = MutableStateFlow<ResultModel<List<VoteModel>>>(ResultModel.none())
    val listVotes: StateFlow<ResultModel<List<VoteModel>>>
        get() = _listVotes

    private val _fullInfoVote = MutableStateFlow<ResultModel<FullInfoVoteModel>>(ResultModel.none())
    val fullInfoVote: StateFlow<ResultModel<FullInfoVoteModel>>
        get() = _fullInfoVote

    fun loadListVotes() {
        viewModelScope.launch {
            getVotesUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _listVotes.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect {
                    _listVotes.value = it
                }
        }
    }

    fun loadFullInfoVote(id: Int) {
        viewModelScope.launch {
            getFullInfoVoteByIdUseCase(id)
                .flowOn(Dispatchers.IO)
                .catch {
                    _fullInfoVote.value = ResultModel.failure("Непредвиденная ошибка.")
                }
                .collect {
                    _fullInfoVote.value = it
                }
        }
    }

    fun sendVote(
        id: Int,
        category: String,
        vote: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            sendVoteUseCase(id, category, vote)
                .flowOn(Dispatchers.IO)
                .catch {
                    onFailure()
                }
                .collect {
                    if (it.status == ResultModel.Status.SUCCESS) {
                        onSuccess()
                    } else if (it.status == ResultModel.Status.FAILURE) {
                        onFailure()
                    }
                }
        }
    }

}