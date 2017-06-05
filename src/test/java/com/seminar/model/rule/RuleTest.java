package com.seminar.model.rule;

import static com.seminar.model.rule.Number.OPERATOR.GREATER_THAN;
import static com.seminar.model.rule.Number.OPERATOR.LESS_THAN;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.seminar.model.entity.Time;

public class RuleTest {

	@Test
	public void notEmpty() throws Exception {
		assertThat(new NotEmpty().applyOn(""), is(false));
		assertThat(new NotEmpty().applyOn("xxx"), is(true));
		assertThat(new NotEmpty().message(), is("can't be empty"));
	}
	
	@Test
	public void numberThreshold() throws Exception {
		Number greater0 = new Number(GREATER_THAN, 0);
		Number less4 = new Number(LESS_THAN, 4);
		Number number = new Number(LESS_THAN, 5);
		
		assertThat(greater0.applyOn("1"), is(true));
		assertThat(greater0.message(), is(""));
		
		assertThat(less4.applyOn("5"), is(false));
		assertThat(less4.message(), is("must be less than 4"));
		
		assertThat(number.applyOn("xxx"), is(false));
		assertThat(number.message(), is("must be a number"));
	}
	
	@Test
	public void timeFormat() throws Exception {
		TimeFormat time = new TimeFormat(Time.FORMAT);
		assertThat(time.applyOn(""),is(false));
		assertThat(time.message(),is("must have " + Time.FORMAT + " format"));
		assertThat(new TimeFormat(Time.FORMAT).applyOn("26.03.2017"), is(true));
	}
	
	@Test
	public void maxLength() throws Exception {
		MaxLength maxLength = new MaxLength(3);
		
		assertThat(maxLength.applyOn("1234"), is(false));
		assertThat(maxLength.applyOn(null), is(false));
		assertThat(maxLength.applyOn("123"), is(true));
		assertThat(maxLength.message(), is("must have no more than 3 chars"));
	}
}
