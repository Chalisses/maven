package chap05;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppCtx {

	@Bean
	public ExeAspect exeAspect() {
		return new ExeAspect();
	}
	
	@Bean
	public Calculator calculator() {
		return new ImplCalculator();
	}
	
	@Bean
	public Calculator calculator2() {
		return new ImplCalculator2();
	}
}
