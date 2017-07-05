package com.seminar.view;

import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.group;
import static com.github.manliogit.javatags.lang.HtmlHelper.table;
import static com.github.manliogit.javatags.lang.HtmlHelper.tbody;
import static com.github.manliogit.javatags.lang.HtmlHelper.text;
import static com.github.manliogit.javatags.lang.HtmlHelper.th;
import static com.github.manliogit.javatags.lang.HtmlHelper.thead;
import static com.github.manliogit.javatags.lang.HtmlHelper.tr;

import java.util.ArrayList;
import java.util.List;

import com.github.manliogit.javatags.element.Element;
import com.seminar.model.entity.Entity;

public class Table implements Html {

	private final Iterable<? extends Entity> _entities;
	private final Iterable<String> _header;
	private final RowFactory _factory;
	
	public Table(Iterable<? extends Entity> entities, Iterable<String> header, RowFactory factory) {
		_entities = entities;
		_header = header;
		_factory = factory;
	}

	private Element header() {
		List<Element> list = new ArrayList<Element>();
		for (String component : _header) {
			list.add(th(text(component)));
		}
		return thead(tr(group(list)));
	}

	private Element body() {
		List<Element> rows = new ArrayList<Element>();
		for (Entity entity : _entities) {
			rows.add(_factory.row(entity));
		}
		return tbody(rows);
	}

	@Override
	public Element build() {
		return table(attr("class -> table table-striped"), header(), body());
	}
}
