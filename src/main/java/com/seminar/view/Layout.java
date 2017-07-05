package com.seminar.view;

import static com.github.manliogit.javatags.lang.HtmlHelper.a;
import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.body;
import static com.github.manliogit.javatags.lang.HtmlHelper.button;
import static com.github.manliogit.javatags.lang.HtmlHelper.div;
import static com.github.manliogit.javatags.lang.HtmlHelper.footer;
import static com.github.manliogit.javatags.lang.HtmlHelper.h1;
import static com.github.manliogit.javatags.lang.HtmlHelper.head;
import static com.github.manliogit.javatags.lang.HtmlHelper.html5;
import static com.github.manliogit.javatags.lang.HtmlHelper.li;
import static com.github.manliogit.javatags.lang.HtmlHelper.link;
import static com.github.manliogit.javatags.lang.HtmlHelper.meta;
import static com.github.manliogit.javatags.lang.HtmlHelper.p;
import static com.github.manliogit.javatags.lang.HtmlHelper.script;
import static com.github.manliogit.javatags.lang.HtmlHelper.span;
import static com.github.manliogit.javatags.lang.HtmlHelper.text;
import static com.github.manliogit.javatags.lang.HtmlHelper.title;
import static com.github.manliogit.javatags.lang.HtmlHelper.ul;

import com.github.manliogit.javatags.element.Element;
import com.seminar.controller.course.AllCourse;
import com.seminar.controller.course.CreateCourse;
import com.seminar.controller.student.StudentController;

public class Layout implements Html {

	private final String _title;
	private final Html _bodyContent;

	private static final String ASSET_VERSION = "?v=1.0.0";
	
	public Layout(String title, Html bodyContent) {
		_title = title;
		_bodyContent = bodyContent;
	}

	@Override
	public Element build() {
		return 
			html5(attr("lang -> en"),
			  head(
			    meta(attr("http-equiv -> content-type","content -> text/html; charset=UTF-8")),
			    meta(attr("charset -> utf-8")),
			    title(_title),
			    meta(attr("name -> viewport","content -> width=device-width, initial-scale=1")),
			    meta(attr("http-equiv -> X-UA-Compatible","content -> IE=edge")),
			    link(attr("rel -> stylesheet","href -> /css/bootstrap.min.css" + ASSET_VERSION,"media -> screen")),
			    link(attr("rel -> stylesheet","href -> /css/custom.css" + ASSET_VERSION)),
			    link(attr("rel -> stylesheet","href -> /css/app.css" + ASSET_VERSION)),
			    text("<!--[if lt IE 9]>"),
			    text("<script src='https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js'></script>"),
			    text("<script src='https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js'></script>"),
			    text("<![endif]-->")
			  ),
			  body(
			    div(attr("class -> navbar navbar-default navbar-fixed-top"),
			      div(attr("class -> container"),
			        div(attr("class -> navbar-header"),
			          a(attr("href -> /","class -> navbar-brand"),
			            text("Seminar")
			          ),
			          button(attr("class -> navbar-toggle","type -> button","data-toggle -> collapse","data-target -> #navbar-main"),
			            span(attr("class -> icon-bar")),
			            span(attr("class -> icon-bar")),
			            span(attr("class -> icon-bar"))
			          )
			        ),
			        div(attr("class -> navbar-collapse collapse","id -> navbar-main"),
			          ul(attr("class -> nav navbar-nav navbar-right"),
			            li(attr("class -> dropdown"),
			              a(attr("class -> dropdown-toggle","data-toggle -> dropdown","href -> #","id -> download","aria-expanded -> false"),
			                text("Account "),
			                span(attr("class -> caret"))
			              ),
			              ul(attr("class -> dropdown-menu","aria-labelledby -> download"),
			                li(
			                  a(attr("href -> /"),
			                    text("settings")
			                  )
			                ),
			                li(attr("class -> divider")),
			                li(
			                  a(attr("href -> /"),
			                    text("logout")
			                  )
			                )
			              )
			            )
			          )
			        )
			      )
			    ),
			    div(attr("class -> container"),
			      div(attr("class -> page-header","id -> banner"),
			        div(attr("class -> row"),
			          div(attr("class -> col-lg-8 col-md-7 col-sm-6"),
			            h1("Seminar" ),
			            p(attr("class -> lead"),
			              text("Manage your courses!")
			            )
			          )
			        ),
			        div(attr("class -> row"),
			          div(attr("class -> col-lg-2 col-md-2 col-sm-3"),
			            div(attr("class -> list-group table-of-contents"),
			              a(attr("class -> list-group-item","href -> " + AllCourse.ROUTE),
			                text("Course List")
			              ),
			              a(attr("class -> list-group-item","href -> " + CreateCourse.ROUTE),
			                text("Create Course")
			              ),
			              a(attr("class -> list-group-item","href -> " + StudentController.ALL),
			                text("Student List")
			              ),
			              a(attr("class -> list-group-item","href -> " + StudentController.CREATE),
			                text("Create Student")
			              )
			            )
			          ),
			          div(attr("class -> col-lg-8 col-md-8 col-sm-9"),
		        		  _bodyContent.build()
			          )
			        )
			      ),
			      footer(
			        div(attr("class -> row"),
			          div(attr("class -> col-lg-12"),
			            p(
			              a(attr("href -> http://bootswatch.com/cerulean","rel -> nofollow"),
			                text("Cerulean theme")
			              )
			            ),
			            p(
			              text("Code released under the "),
			              a(attr("href -> https://github.com/thomaspark/bootswatch/blob/gh-pages/LICENSE"),
			                text("MIT License")
			              ),
			              text(".")
			            ),
			            p(
			              text("GmTechnologies")
			            )
			          )
			        )
			      )
			    ),
			    script(attr("src -> /js/jquery.min.js" + ASSET_VERSION)),
			    script(attr("src -> /js/bootstrap.min.js" + ASSET_VERSION)),
			    script(attr("src -> /js/custom.js" + ASSET_VERSION))
			  )
			);
	}
}
