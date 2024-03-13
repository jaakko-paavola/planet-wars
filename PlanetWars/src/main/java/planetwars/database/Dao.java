/*
MIT License
Copyright (c) 2019 Jaakko Paavola
*/

package planetwars.database;

import java.sql.SQLException;

/**
 * The interface determining how all Dao's should look like.
 * @author jaakkpaa
 */
public interface Dao<T, K> {
	T findOne(K key) throws SQLException, Exception;
	void saveOrUpdate(T element) throws SQLException, Exception;
	void createTableIfNotExist();
	void delete(K key) throws SQLException, Exception;
}
