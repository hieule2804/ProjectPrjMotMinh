/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import model.Account;
import model.card;
import model.item;

/**
 *
 * @author ADMIN
 */
public class OrderDAO extends DBContext {

    public void addOrder(Account a, card card) {
        LocalDate curdate = java.time.LocalDate.now();
        String date = curdate.toString();
        try {
            //add vao bang order
            String sql = "INSERT INTO [dbo].[Order]\n"
                    + "           ([cusName]\n"
                    + "           ,[date]\n"
                    + "           ,[totalmoney])\n"
                    + "     VALUES\n"
                    + "           (?,?,?)";
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, a.getUsername());
            statement.setString(2, date);
            statement.setInt(3, card.getTotalMoney());
            statement.executeUpdate();
            //lay ra id cua order vua add
            String sql1 = "select top 1 id from[Order] order by id desc";
            PreparedStatement pr  = connection.prepareStatement(sql1);
            resultSet = pr.executeQuery();
            //add vao bang order
            if (resultSet.next()) {
                int oid = resultSet.getInt(1);
                for (item o : card.getItem()) {
                    String sql2 = "INSERT INTO [dbo].[OrderDetail]\n"
                            + "           ([oid]\n"
                            + "           ,[bid]\n"
                            + "           ,[quantity]\n"
                            + "           ,[price])\n"
                            + "     VALUES\n"
                            + "           (?,?,?,?)";
                     PreparedStatement pr1 = connection.prepareStatement(sql2);
                    pr1.setInt(1, oid);
                    pr1.setInt(2, o.getBook().getId());
                    pr1.setInt(3, o.getQuantity());
                    pr1.setInt(4, o.getPrice());
                    pr1.executeUpdate();
                }
            }
        } catch (Exception e) {
        }
    }
}
