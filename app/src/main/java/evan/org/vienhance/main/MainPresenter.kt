package evan.org.vienhance.main

import evan.org.vienhance.util.Objects.*
import android.content.Context

/**
 * Create By yejiaquan in 2018/10/16 12:32
 */
class MainPresenter internal constructor (view: MainContract.View, context: Context): MainContract.Presenter {

    private val view : MainContract.View = checkNotNull(view, "view cannot be null!")
    private val context : Context = checkNotNull(context, "context cannot be null!")

    init {
        view.setPresenter(this)
    }

    override fun start() {

    }
}
