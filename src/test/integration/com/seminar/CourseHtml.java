package com.seminar;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CourseHtml {

	@Test
	public void homePage() throws Exception {
		WebClient webClient = new WebClient();

		HtmlPage page = webClient.getPage("http://localhost:8080");

		assertThat(page.getTitleText(), is("courses"));
		assertThat(page.asText(), containsString("Manage your courses!"));
	}
}
