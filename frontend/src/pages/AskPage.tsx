import { useState, useEffect } from "react";
import {
  Box,
  Button,
  Divider,
  Flex,
  Heading,
  Stack,
  Text,
  Textarea,
  Image,
  Progress,
} from "@chakra-ui/react";
import axios from "axios";
import { FaqDto } from "../types/FaqDto";

const apiUrl = import.meta.env.VITE_API_URL || "";

const AskPage = () => {
  const [question, setQuestion] = useState("");
  const [faqResponse, setFaqResponse] = useState<FaqDto | null>(null);
  const [recentQuestions, setRecentQuestions] = useState<FaqDto[]>([]);

  const handleAsk = async () => {
    try {
      const response = await axios.get<FaqDto>(`${apiUrl}/faq/ask`, {
        params: { question },
      });
      setFaqResponse(response.data);
    } catch (error) {
      console.error("Error fetching answer:", error);
    }
  };

  useEffect(() => {
    axios
      .get<FaqDto[]>(`${apiUrl}/faq?validated=true`)
      .then((res) => setRecentQuestions(res.data.slice(-20).reverse()))
      .catch((err) => console.error("Failed to fetch recent questions", err));
  }, []);

  return (
    <Flex direction="column" align="center" mt={10}>
      <Box width="100%" maxW="800px" p={4} textAlign="left">
        <Flex align="center" mb={6}>
          <Image src="/ai-faq-image.png" alt="AI Assistant" height="90px" mr={4} />
          <Box>
            <Heading as="h2" size="lg">
              AI-assisted FAQ
            </Heading>
            <Text fontSize="md" color="gray.600">
              for the Real-Time Log Analytics Project
            </Text>
          </Box>
        </Flex>

        <Textarea
          value={question}
          onChange={(e) => setQuestion(e.target.value)}
          placeholder="Type your question here..."
          size="lg"
          height="120px"
          mb={4}
          fontSize="lg"
        />
        <Button
          colorScheme="blue"
          onClick={handleAsk}
          isDisabled={!question.trim()}
          width="fit-content"
        >
          Submit
        </Button>
      </Box>

      {faqResponse && (
        <Box mt={6} width="100%" maxW="780px" p={4} borderWidth={1} borderRadius="md">
          <Heading as="h3" size="md" mb={2}>
            💬 AI Answer
          </Heading>
          <Text fontSize="lg" mb={6}>
            {faqResponse.answer}
          </Text>

          {faqResponse.contextItems && faqResponse.contextItems.length > 0 && (
            <>
              <Divider mb={4} />
              <Text fontSize="sm" fontWeight="semibold" color="gray.600" mb={3}>
                Context Items:
              </Text>
              {faqResponse.contextItems.map((item, index) => (
                <Box key={index} mb={3} p={2} borderWidth="1px" borderRadius="md" bg="gray.50">
                  <Flex justify="space-between" align="center" mb={1}>
                    <Text fontSize="sm" fontWeight="bold">
                      {index + 1}
                    </Text>
                    <Flex align="center" gap={2}>
                      <Progress value={item.score * 100} size="xs" colorScheme="green" width="150px" />
                      <Text fontSize="xs" color="gray.600">
                        {(item.score * 100).toFixed(2)}%
                      </Text>
                    </Flex>
                  </Flex>
                  <Text fontSize="sm" color="gray.700">
                    {item.text}
                  </Text>
                </Box>
              ))}
            </>
          )}
        </Box>
      )}

      <Box mt={10} width="100%" maxW="780px" p={4} borderWidth={1} borderRadius="md">
        <Heading as="h3" size="md" mb={2}>
          📜 Recent Validated Questions
        </Heading>
        <Stack spacing={3}>
          {recentQuestions.map((q) => (
            <Box key={q.id} borderWidth="1px" borderRadius="md" p={3} bg="gray.50">
              <Text fontWeight="bold" fontSize="sm">
                {q.question}
              </Text>
              <Text fontSize="xs" color="gray.600" mt={1}>
                {q.answer}
              </Text>
            </Box>
          ))}
        </Stack>
      </Box>
    </Flex>
  );
};

export default AskPage;