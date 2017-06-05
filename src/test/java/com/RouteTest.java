package com;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RouteTest {

	@Test
	public void toStringReplaceAdditionalRegExinfo() throws Exception {
		assertThat(new Route("/").toString(), is("/"));
		assertThat(new Route("/course/create/?").toString(), is("/course/create"));
		assertThat(new Route("/course/create/[0-9]+").toString(), is("/course/create"));
	}
}
