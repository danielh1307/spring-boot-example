package danielh1307.springbootexample.configuration;

import danielh1307.springbootexample.configuration.ApplicationConfig.BarService;
import danielh1307.springbootexample.configuration.ApplicationConfig.FooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationConfigTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void testContext() {
        assertThat(ctx.containsBean("fooService"), is(true));
        FooService fooService = ctx.getBean(FooService.class);
        assertThat(fooService.name, is(equalTo("I am a Foo service")));

        assertThat(ctx.containsBean("barServiceSpecialName"), is(true));
        BarService barService = ctx.getBean(BarService.class);
        assertThat(barService.name, is(equalTo("I am a Bar service")));
    }

    @Test
    public void testFooServiceIsSingleton() {
        FooService fooService = ctx.getBean(FooService.class);
        BarService barService = ctx.getBean(BarService.class);
        assertSame(fooService, barService.fooService);
    }

    @Test
    public void testLegacyConfigIsIncluded() {
        assertThat(ctx.containsBean("legacyConfig"), is(true));
    }
}
