package com.example.FoodMicroservice_DM2.Service;

import com.example.FoodMicroservice_DM2.Entity.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addFood(String name, String description, MultipartFile file, Double price) throws IOException {
        String sql = "INSERT INTO food_table (food_name, food_description, food_pic, food_price) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            try
            {
                ps.setBytes(3, file.getBytes());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Error reading image file", e);
            }
            ps.setDouble(4, price);
            return ps;
        });
    }

    public List<Food> getAllFoods() {
        String sql = "SELECT * FROM food_table";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
            return new Food(
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getString("food_description"),
                    rs.getBytes("food_pic"),
                    rs.getDouble("food_price")
            );
        });
    }

    public Food updateFood(int id, String name, String description, MultipartFile file, Double price) throws IOException {
        String sql = "UPDATE food_table SET food_name = ?, food_description = ?, food_pic = ?, food_price = ? WHERE food_id = ?";
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            try
            {
                ps.setBytes(3, file.getBytes());
            }
            catch (IOException e)
            {
                throw new RuntimeException("Error reading image file", e);
            }
            ps.setDouble(4, price);
            ps.setInt(5, id);
            return ps;
        });

        return getFoodById(id);
    }

    public void deleteFood(int id)
    {
        String sql = "DELETE FROM food_table WHERE food_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Food> searchFoodByName(String name) {
        String sql = "SELECT * FROM food_table WHERE LOWER(food_name) LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + name.toLowerCase() + "%"}, (rs, rowNum) -> {
            return new Food(
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getString("food_description"),
                    rs.getBytes("food_pic"),
                    rs.getDouble("food_price")
            );
        });
    }

    public List<Food> filterByPriceRange(Double minPrice, Double maxPrice) {
        String sql = "SELECT * FROM food_table WHERE food_price BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new Object[]{minPrice, maxPrice}, (rs, rowNum) -> {
            return new Food(
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getString("food_description"),
                    rs.getBytes("food_pic"),
                    rs.getDouble("food_price")
            );
        });
    }

    public Food getFoodById(int id) {
        String sql = "SELECT * FROM food_table WHERE food_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            return new Food(
                    rs.getInt("food_id"),
                    rs.getString("food_name"),
                    rs.getString("food_description"),
                    rs.getBytes("food_pic"),
                    rs.getDouble("food_price")
            );
        });
    }
}