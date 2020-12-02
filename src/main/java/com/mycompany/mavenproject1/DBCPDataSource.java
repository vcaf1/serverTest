/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
        private static BasicDataSource ds = new BasicDataSource();
    
    static{
        /**
285     * The connection URL to be passed to our JDBC driver to establish a connection.
* Note: this method currently has no effect once the pool has been initialized. The pool is initialized the first time one of the following methods is invoked: getConnection, setLogwriter, setLoginTimeout, getLoginTimeout, getLogWriter.
286     */
        ds.setUrl("jdbc:mysql://localhost:3306/luchtmodule?useSSL=false");
        /**
290     * The connection user name to be passed to our JDBC driver to establish a connection.
291     */
        ds.setUsername("luchtmodule");
        /**
280     * The connection password to be passed to our JDBC driver to establish a connection.
281     */
        ds.setPassword("luchtmodule");
        /**
188     * The minimum number of active connections that can remain idle in the pool, without extra ones being created when
189     * the evictor runs, or 0 to create none. The pool attempts to ensure that minIdle connections are available when
190     * the idle object evictor runs. The value of this property has no effect unless
191     * {@link #timeBetweenEvictionRunsMillis} has a positive value.
192     */
        ds.setMinIdle(5);
        /**
178     * The maximum number of connections that can remain idle in the pool, without extra ones being destroyed, or
179     * negative for no limit. If maxIdle is set too low on heavily loaded systems it is possible you will see
180     * connections being closed and almost immediately new connections being opened. This is a result of the active
181     * threads momentarily closing connections faster than they are opening them, causing the number of idle connections
182     * to rise above maxIdle. The best value for maxIdle for heavily loaded system will vary but the default is a good
183     * starting point.
184     */
        ds.setMaxIdle(10);
        /**
215     * <p>
216     * The maximum number of open statements that can be allocated from the statement pool at the same time, or negative
217     * for no limit. Since a connection usually only uses one or two statements at a time, this is mostly used to help
218     * detect resource leaks.
219     * </p>
220     * <p>
221     * Note: As of version 1.3, CallableStatements (those produced by {@link Connection#prepareCall}) are pooled along
222     * with PreparedStatements (produced by {@link Connection#prepareStatement}) and
223     * <code>maxOpenPreparedStatements</code> limits the total number of prepared or callable statements that may be in
224     * use at a given time.
225     * </p>
226     */
        ds.setMaxOpenPreparedStatements(100);
    }
    
    /**
715     * Creates (if necessary) and return a connection to the database.
716     *
717     * @throws SQLException if a database access error occurs
718     * @return a database connection
719     */
    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
    
    private DBCPDataSource(){
        
    }
}
