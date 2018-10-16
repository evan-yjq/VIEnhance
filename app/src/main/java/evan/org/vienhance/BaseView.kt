package evan.org.vienhance

/**
 * Create By yejiaquan in 2018/10/16 11:51
 */
interface BaseView<T : BasePresenter> {

    val isActive: Boolean

    fun setPresenter(presenter: T)
}
