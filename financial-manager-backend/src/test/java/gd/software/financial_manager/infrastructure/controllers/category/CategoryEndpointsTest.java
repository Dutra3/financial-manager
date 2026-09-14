package gd.software.financial_manager.infrastructure.controllers.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import gd.software.financial_manager.domain.model.Category;
import gd.software.financial_manager.domain.model.CategoryType;
import gd.software.financial_manager.domain.usecase.category.CreateCategory;
import gd.software.financial_manager.domain.usecase.category.DeleteCategory;
import gd.software.financial_manager.domain.usecase.category.FetchCategory;
import gd.software.financial_manager.infrastructure.dtos.CategoryData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateCategory createCategory;

    @MockBean
    private FetchCategory fetchCategory;

    @MockBean
    private DeleteCategory deleteCategory;

    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Test
    void should_create_category_and_return_201() throws Exception {
        CategoryData request = new CategoryData(null, "Food", "DEBIT");
        Category saved = new Category(CATEGORY_ID, "Food", CategoryType.DEBIT);
        when(createCategory.use(any(Category.class))).thenReturn(saved);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CATEGORY_ID.toString()))
                .andExpect(jsonPath("$.name").value("Food"))
                .andExpect(jsonPath("$.type").value("DEBIT"));

        verify(createCategory).use(any(Category.class));
    }

    @Test
    void should_fetch_category_by_id_and_return_200() throws Exception {
        Category category = new Category(CATEGORY_ID, "Transport", CategoryType.CREDIT);
        when(fetchCategory.by(CATEGORY_ID)).thenReturn(category);

        mockMvc.perform(get("/categories/{id}", CATEGORY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CATEGORY_ID.toString()))
                .andExpect(jsonPath("$.name").value("Transport"))
                .andExpect(jsonPath("$.type").value("CREDIT"));
    }

    @Test
    void should_fetch_all_categories_and_return_200() throws Exception {
        Category food = new Category(UUID.randomUUID(), "Food", CategoryType.DEBIT);
        Category transport = new Category(UUID.randomUUID(), "Transport", CategoryType.CREDIT);
        when(fetchCategory.all()).thenReturn(List.of(food, transport));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Food"))
                .andExpect(jsonPath("$[1].name").value("Transport"));
    }

    @Test
    void should_delete_category_and_return_204() throws Exception {
        doNothing().when(deleteCategory).remove(CATEGORY_ID);

        mockMvc.perform(delete("/categories/{id}", CATEGORY_ID))
                .andExpect(status().isNoContent());

        verify(deleteCategory).remove(CATEGORY_ID);
    }
}
