package evan.org.vienhance

import evan.org.vienhance.domain.model.AlgContext

/**
 * Create By yejiaquan in 2018/10/16 11:51
 */
interface BaseView<T : BasePresenter> {

    val isActive: Boolean

    fun setPresenter(presenter: T)

    fun setContext(context: AlgContext)
}
