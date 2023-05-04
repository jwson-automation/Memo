package com.ssafy.jetpackall

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.jetpackall.database.NotesDto
import com.ssafy.jetpackall.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val KEY_TITLE = "title"
const val KEY_BODY = "body"
const val KEY_ROWID = "_id"

class NoteEditActivity : AppCompatActivity() {

    private lateinit var mTitleText: EditText
    private lateinit var mBodyText: EditText
    private val vm: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        val id = intent.extras?.getLong(KEY_ROWID) ?: -1L
        vm.setId(id)


        findViewById<Button>(R.id.confirm).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun initView() {
        mTitleText = findViewById<View>(R.id.title) as EditText
        mBodyText = findViewById<View>(R.id.body) as EditText

        if (vm._id != -1L) {
            var note = NotesDto()
            vm.note.observe(this) {
                mTitleText.setText(note.TITLE)
                mBodyText.setText(note.BODY)
            }

        }
    }

    //비정상 종료시는 id를 기록한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ROWID, vm._id)
    }

    //pause에서 저장하도록 구현
    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    //-1은 앞 Activity에서 입력(insert)으로 넘어온 경우
    private fun saveState() {
        val title = mTitleText.text.toString()
        val body = mBodyText.text.toString()
        if (vm._id == -1L) {
            CoroutineScope(Dispatchers.IO).launch {
                vm.insert(NotesDto(title, body))
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                vm.update(NotesDto(vm._id, title, body))
            }
        }
    }
}