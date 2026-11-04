import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources("classpath:config.properties")
public interface TestConfig extends Config {

    @Key("base.url")
    @DefaultValue("http://localhost:8080")
    String baseUrl();

    @Key("swagger.url")
    @DefaultValue("http://localhost:8080/swagger-ui/index.html")
    String swaggerUrl();

    @Key("browser.name")
    @DefaultValue("chrome")
    String browserName();

    @Key("browser.size")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("timeout")
    @DefaultValue("10")
    String timeout();

    @Key("admin.login")
    @DefaultValue("admin")
    String adminLogin();

    @Key("admin.password")
    @DefaultValue("secret123")
    String adminPassword();
}