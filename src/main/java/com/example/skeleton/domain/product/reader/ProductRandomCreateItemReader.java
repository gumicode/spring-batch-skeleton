package com.example.skeleton.domain.product.reader;

import com.example.skeleton.domain.product.entity.ProductEntity;
import com.example.skeleton.global.util.NamingUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@StepScope
@Component(ProductRandomCreateItemReader.BEAN_NAME)
public class ProductRandomCreateItemReader implements ItemReader<ProductEntity> {

	public static final String BEAN_NAME = "PRODUCT_RANDOM_CREATE_ITEM_READER";

	@Override
	public ProductEntity read() {

		String code = RandomString.make();
		String name = NamingUtil.food();
		String image = code + ".png";

		return ProductEntity.builder()
				.code(code)
				.name(name)
				.image(image)
				.build();
	}
}
