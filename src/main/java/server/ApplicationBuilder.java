package server;

public class ApplicationBuilder {
    private Handler application;

    private ApplicationBuilder(Handler application) {
        this.application = application;
    }

    public static ApplicationBuilder forHandler(Handler application) {
        return new ApplicationBuilder(application);
    }

    public ApplicationBuilder use(Middleware middleware) {
        this.application = middleware.apply(application);
        return this;
    }

    public Handler build() {
        return application;
    }
}
