package evan.org.vienhance.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import evan.org.vienhance.R

/**
 * Create By yejiaquan in 2018/10/16 12:33
 */
class MainFragment : Fragment(), MainContract.View {

    private var presenter: MainContract.Presenter? = null

    override fun onResume() {
        super.onResume()
        presenter!!.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.main_frag, container, false)
        return root
    }

    override val isActive: Boolean
        get() {
            return isAdded
        }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    companion object {

        internal fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
