package com.kgb.noteseditor.utilities

import com.kgb.noteseditor.database.NoteEntity
import java.util.*
import kotlin.collections.ArrayList

final class SampleData {
    companion object {
        private const val SAMPLE_TEXT_1 = "A simple note";
        private const val SAMPLE_TEXT_2 = "A note with a\nline feed";
        private const val SAMPLE_TEXT_3 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
            "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, " +
            "eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam " +
            "voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione " +
            "voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci " +
            "velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. " +
            "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex " +
            "ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil " +
            "molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

        fun getDate(diff: Int): Date {
            val callendar = GregorianCalendar()
            callendar.add(Calendar.MILLISECOND, diff)
            return callendar.time
        }

        fun getNotes(): List<NoteEntity> {
            val list = ArrayList<NoteEntity>()
            list.add(NoteEntity(date = getDate(0), text = SAMPLE_TEXT_1))
            list.add(NoteEntity(date = getDate(-1), text = SAMPLE_TEXT_2))
            list.add(NoteEntity(date = getDate(-2), text = SAMPLE_TEXT_3))
            return list
        }
    }
}