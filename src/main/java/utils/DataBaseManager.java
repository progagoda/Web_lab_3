package utils;
import entity.Result;


import javax.annotation.Resource;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
@ManagedBean(name="DataBase")
public class DataBaseManager {
    private  Connection connection;
    private  Statement statement;
    private  boolean connected;
    @Resource(lookup = "java:/PostgresDS")
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public boolean connect(Connection connection) {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = connection;
            System.out.println("GOOD!");
            if (connection != null) {
                System.out.println("Успешное подключение к базе данных");
                statement = connection.createStatement();
                connected = true;
                return true;
            } else {
                System.out.println("Не удалось подключиться к базе данных!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver не найден. Подключите библиотеку PostgreSQL!");
            System.out.println(e.getMessage());
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        connected = false;
        return false;
    }

    public boolean addBean(Result result) {
        String select = "INSERT INTO Hit(x, y, r, currenttime, execution_time, is_hit , session_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String id =FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setDouble(1, result.getX());
            preparedStatement.setDouble(2, result.getY());
            preparedStatement.setDouble(3, result.getR());
            preparedStatement.setString(4, result.getCurrentTime());
            preparedStatement.setDouble(5, result.getExecutionTime());
            preparedStatement.setBoolean(6, result.isResult());
            preparedStatement.setString(7, id);
            if (preparedStatement.executeUpdate() != 0)
                return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении бина в базу" + e.getMessage());
            return false;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException при добавлении в базу");
            return false;
        }
        return false;
    }

    public  void load(List<Result> list) throws SQLException {
        connect(dataSource.getConnection());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String id = facesContext.getExternalContext().getSessionId(false);
        boolean flag;
        String request = "SELECT * from Hit WHERE SESSION_ID = '" + id + "'";
        try {
            ResultSet resultSet = getStatement().executeQuery(request);
            while (resultSet.next()) {
                flag = true;
                float corX = resultSet.getFloat("x");
                float corY = resultSet.getFloat("y");
                float corR = resultSet.getFloat("r");
                String currentTime = resultSet.getString("currenttime");
                long exTime = resultSet.getLong("execution_time");
                boolean isHit = resultSet.getBoolean("is_hit");
                String session = resultSet.getString("SESSION_ID");
                Result result = new Result();
                if (valX(corX) && valY(corY) && valR(corR)) {
                    result.setX(corX);
                    result.setY(corY);
                    result.setR(corR);
                    result.setCurrentTime(currentTime);
                    result.setExecutionTime(exTime);
                    result.setResult(isHit);
                    result.setSession_id(session);
                    for (Result vals : list) {
                        if (vals.equals(result)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(result);
                }
            }
        } catch (Exception e) {
            System.out.println("Ex in load block!" + e.getMessage());
        }
    }

    public static boolean valX(Float x) {
        return x != null;
    }

    public static boolean valY(Float y) {
        return y != null;
    }

    public static boolean valR(Float r) {
        return r != null;
    }

    public boolean clear() {
        String request = "TRUNCATE TABLE RESULTS";
        execute(request);
        return true;
    }
    public  boolean execute(String request) {
        try {
            if (statement.execute(request)) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public  Statement getStatement() {
        return statement;
    }

    public  boolean isConnected() {
        return connected;
    }
}
