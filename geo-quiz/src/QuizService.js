import axios from 'axios';

const QUIZ_API_BASE_URL = "http://localhost:8080/api/quiz";

class QuizService {
    getAllQuizzes() {
        return axios.get(QUIZ_API_BASE_URL);
    }
    async getQuizById(id) {
        try {
            const response = await axios.get(`${QUIZ_API_BASE_URL}/quiz/${id}`);
            return response.data;
        } catch (error) {
            console.error(`Error fetching quiz by ID ${id}:`, error);
            throw error;
        }
    }
}

export default new QuizService();
