package com.example.FoodMicroservice_DM2.Service;

import com.example.FoodMicroservice_DM2.Entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addFood(String name, String description, MultipartFile file, Double price, String category) throws IOException {
        jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call add_food(?, ?, ?, ?, ?)}");
            cs.setString(1, name);
            cs.setString(2, description);
            try
            {
                cs.setBlob(3, file.getInputStream());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            cs.setDouble(4, price);
            cs.setString(5, category);
            cs.execute();
            return null;
        });
    }

    public void updateFood(int id, String name, String description, MultipartFile file, Double price, String category) throws IOException {
        jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call update_food(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, name);
            cs.setString(3, description);
            try
            {
                cs.setBlob(3, file.getInputStream());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            cs.setDouble(5, price);
            cs.setString(6, category);
            cs.execute();
            return null;
        });
    }

    public void deleteFood(int id)
    {
        jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call delete_food(?)}");
            cs.setInt(1, id);
            cs.execute();
            return null;
        });
    }

    public List<Food> getAllFoods() {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call get_all_foods(?)}");
            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);

            List<Food> foodList = new ArrayList<>();
            while (rs.next())
            {
                foodList.add(new Food(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBytes("food_pic"),
                        rs.getDouble("food_price"),
                        rs.getString("category_name")
                ));
            }
            return foodList;
        });
    }

    public Food getFoodById(int id)
    {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call search_by_id(?, ?)}");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.REF_CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(2);
            Food food = null;
            if (rs.next())
            {
                food = new Food(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBytes("food_pic"),
                        rs.getDouble("food_price"),
                        rs.getString("category_name")
                );
            }
            return food;
        });
    }

    public List<Food> searchFoodByName(String name) {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call search_by_name(?, ?)}");
            cs.setString(1, name);
            cs.registerOutParameter(2, Types.REF_CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(2);

            List<Food> foodList = new ArrayList<>();
            while (rs.next())
            {
                foodList.add(new Food(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBytes("food_pic"),
                        rs.getDouble("food_price"),
                        rs.getString("category_name")
                ));
            }
            return foodList;
        });
    }

    public List<Food> filterByPrice(Double min, Double max)
    {
        return jdbcTemplate.execute((Connection conn) -> {
            CallableStatement cs = conn.prepareCall("{call search_by_price(?, ?, ?)}");
            cs.setDouble(1, min);
            cs.setDouble(2, max);
            cs.registerOutParameter(3, Types.REF_CURSOR);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(3);

            List<Food> foodList = new ArrayList<>();
            while (rs.next())
            {
                foodList.add(new Food(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getString("food_description"),
                        rs.getBytes("food_pic"),
                        rs.getDouble("food_price"),
                        rs.getString("category_name")
                ));
            }
            return foodList;
        });
    }
}