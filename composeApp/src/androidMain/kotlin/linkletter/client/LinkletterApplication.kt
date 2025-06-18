package linkletter.client

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LinkletterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(
            linkletterAppDeclaration {
                androidContext(this@LinkletterApplication)
            },
        )
    }
}
