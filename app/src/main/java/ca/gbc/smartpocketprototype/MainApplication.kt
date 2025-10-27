package ca.gbc.smartpocketprototype

import android.app.Application
import ca.gbc.smartpocketprototype.data.AppDatabase


class MainApplication {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
}
