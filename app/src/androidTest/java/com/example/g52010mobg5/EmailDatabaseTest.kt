package com.example.g52010mobg5

/*import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.g52010mobg5.app.database.email.Email
import com.example.g52010mobg5.app.database.email.EmailDatabase
import com.example.g52010mobg5.app.database.email.EmailDatabaseDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EmailDatabaseTest {

    private lateinit var emailDao: EmailDatabaseDao
    private lateinit var db: EmailDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, EmailDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        emailDao = db.emailDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEmail() {
        val email = Email("azerty@uwu.com", "10")
        emailDao.insert(email)
        val emailTest = emailDao.get("azerty@uwu.com")
        Assert.assertEquals(emailTest?.emailId, "azerty@uwu.com")
    }
}*/