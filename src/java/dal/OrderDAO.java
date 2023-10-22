/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.BestSellingProduct;
import model.card;
import model.item;
import model.order;
import model.orderDetail;

/**
 *
 * @author ADMIN
 */
public class OrderDAO extends DBContext {

    bookDao bookdao = new bookDao();

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
            PreparedStatement pr = connection.prepareStatement(sql1);
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

    public List<order> getlistOrder() {
        connection = getConnection();
        List<order> list = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[cusName]\n"
                + "      ,[date]\n"
                + "      ,[totalmoney]\n"
                + "  FROM [dbo].[Order]";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String cusname = resultSet.getString("cusName");
                Date date = resultSet.getDate("date");
                int totalMoney = resultSet.getInt("totalmoney");
                order o = new order();
                o.setId(id);
                o.setCusname(cusname);
                o.setDate(date);
                o.setTotaloney(totalMoney);
                list.add(o);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<orderDetail> getListDetail(int id) {
        List<orderDetail> list = new ArrayList<>();
        connection = getConnection();
        String sql = "SELECT [oid]\n"
                + "      ,[bid]\n"
                + "      ,[quantity]\n"
                + "      ,[price]\n"
                + "  FROM [dbo].[OrderDetail]"
                + "where oid =?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int oid = resultSet.getInt("oid");
                int bid = resultSet.getInt("bid");
                int quan = resultSet.getInt("quantity");
                int price = resultSet.getInt("price");
                orderDetail o = new orderDetail();
                o.setOid(oid);
                o.setBid(bid);

                o.setQuantity(quan);
                o.setPrice(price);
                list.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public String getTheMostUser() {
        connection = getConnection();
        String u = "";
        String sql = "SELECT top 1 [cusName] \n"
                + "  FROM [dbo].[Order]\n"
                + "  group by [cusName]\n"
                + "  order by [cusName] asc";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                u = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public List<BestSellingProduct> getbestSelling() {
        List<BestSellingProduct> list = new ArrayList<>();
        connection = getConnection();
        String sql = "WITH BidCounts AS (\n"
                + "    SELECT [bid], COUNT([bid]) AS BidCount\n"
                + "    FROM [dbo].[OrderDetail]\n"
                + "    GROUP BY [bid]\n"
                + ")\n"
                + "SELECT [bid], BidCount\n"
                + "FROM BidCounts\n"
                + "WHERE BidCount = (SELECT MAX(BidCount) FROM BidCounts);";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("bid");
                int count = resultSet.getInt("BidCount");
                BestSellingProduct b = new BestSellingProduct();
                b.setName(name);
                b.setCount(count);
                list.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
