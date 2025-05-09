import { useEffect, useState } from 'react';
import axios from 'axios';
import {
  Box,
  Text,
  Heading,
  VStack,
  Button,
  Textarea,
  useToast,
} from '@chakra-ui/react';

// Typage basé sur FaqDto
interface FaqDto {
  id: string;
  question: string;
  answer: string;
  validated: boolean;
  autoGenerated: boolean;
  askedAt: string;  // ISO string pour LocalDateTime
  confidenceScore: number;
  contextItems: string[];
}

const apiUrl = import.meta.env.VITE_API_URL || '';

const AdminPage = () => {
  const [questions, setQuestions] = useState<FaqDto[]>([]);
  const [editedAnswers, setEditedAnswers] = useState<{ [id: string]: string }>({});
  const toast = useToast();

  useEffect(() => {
    axios.get(`${apiUrl}/faq?validated=false`)
      .then((res) => {
        const data: FaqDto[] = Array.isArray(res.data) ? res.data : [];
        setQuestions(data.slice().reverse());
      })
      .catch(() => toast({ title: "Error fetching questions", status: "error" }));
  }, []);

  const validateAnswer = (faq: FaqDto) => {
    const correctedAnswer = editedAnswers[faq.id] ?? faq.answer;
    const payload = { ...faq, answer: correctedAnswer };

    axios.patch(`${apiUrl}/faq/validate`, payload)
      .then(() => {
        setQuestions(prev => prev.filter(q => q.id !== faq.id));
        toast({ title: "Answer validated", status: "success" });
      })
      .catch(() => toast({ title: "Error validating", status: "error" }));
  };

  const deleteQuestion = (id: string) => {
    axios.delete(`${apiUrl}/faq/${id}`)
      .then(() => {
        setQuestions(prev => prev.filter(q => q.id !== id));
        toast({ title: "Question deleted", status: "info" });
      })
      .catch(() => toast({ title: "Error deleting question", status: "error" }));
  };

  return (
    <Box>
      <Heading mb={4}>Admin Panel</Heading>
      <VStack spacing={6} align="stretch">
        {questions.map((q) => (
          <Box key={q.id} p={4} borderWidth="1px" borderRadius="md">
            <Text fontWeight="bold">Q: {q.question}</Text>
            <Textarea
              mt={2}
              defaultValue={q.answer}
              onChange={(e) => setEditedAnswers({ ...editedAnswers, [q.id]: e.target.value })}
            />
            <Button mt={2} colorScheme="green" onClick={() => validateAnswer(q)}>
              Validate
            </Button>
            <Button mt={2} ml={2} colorScheme="gray" onClick={() => deleteQuestion(q.id)}>
              Delete
            </Button>
          </Box>
        ))}
        {questions.length === 0 && <Text>No unvalidated questions ✨</Text>}
      </VStack>
    </Box>
  );
};

export default AdminPage;
