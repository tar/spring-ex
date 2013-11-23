package com.mycelium.exsp.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mycelium.exsp.model.entities.Entity;

public class EntityUtils {
	public static <S extends Entity> Map<Long, S> asMap(Collection<S> col) {
		Map<Long, S> result = new TreeMap<Long, S>();
		for (S s : col) {
			result.put(s.getId(), s);
		}
		return result;
	}

	public static List<Long> extractIds(Collection<? extends Entity> entities) {
		List<Long> result = new ArrayList<Long>(entities.size());
		for (Entity entity : entities) {
			result.add(entity.getId());
		}
		return result;
	}

}
