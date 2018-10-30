package evan.org.vienhance;

import evan.org.vienhance.domain.enhanceUseCase;

/**
 * Create By yejiaquan in 2018/10/25 14:33
 */
public class Injection {
    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }

    public static enhanceUseCase provideLaplace(){
        return new enhanceUseCase();
    }
}
