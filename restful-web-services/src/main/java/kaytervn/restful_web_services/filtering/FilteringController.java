package kaytervn.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue getBeans() {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3");
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeansFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(new SomeBeans("value1", "value2", "value3"));
		mapping.setFilters(filters);
		return mapping;
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue getListBeans() {
		List<SomeBeans> listBeans = Arrays.asList(new SomeBeans("value1", "value2", "value3"),
				new SomeBeans("value11", "value22", "value33"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeansFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(listBeans);
		mapping.setFilters(filters);

		return mapping;
	}
}
