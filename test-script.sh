#!/bin/bash

echo "========================================="
echo "  University Management System - Tests"
echo "========================================="

BASE_URL="http://localhost:8080"
PASS=0
FAIL=0
TIMESTAMP=$(date +%s)

echo ""

# Test 1: Create Student
echo "Test 1: Create Student"
STUDENT_RESPONSE=$(curl -s -X POST $BASE_URL/api/students \
  -H "Content-Type: application/json" \
  -d "{\"studentCode\":\"STU_${TIMESTAMP}\",\"fullName\":\"John Doe\",\"email\":\"john${TIMESTAMP}@test.com\",\"phone\":\"1234567890\",\"dateOfBirth\":\"2000-01-15\"}")
STUDENT_ID=$(echo "$STUDENT_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d: -f2)
if [ -n "$STUDENT_ID" ]; then
  echo "✅ PASS (Student ID: $STUDENT_ID)"
  PASS=$((PASS+1))
else
  echo "❌ FAIL: $STUDENT_RESPONSE"
  FAIL=$((FAIL+1))
fi

# Test 2: Create Course
echo ""
echo "Test 2: Create Course"
COURSE_RESPONSE=$(curl -s -X POST $BASE_URL/api/courses \
  -H "Content-Type: application/json" \
  -d "{\"courseCode\":\"CS_${TIMESTAMP}\",\"title\":\"Software Engineering\",\"credits\":3,\"maxCapacity\":30,\"teacherName\":\"Dr. Smith\"}")
COURSE_ID=$(echo "$COURSE_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | cut -d: -f2)
if [ -n "$COURSE_ID" ]; then
  echo "✅ PASS (Course ID: $COURSE_ID)"
  PASS=$((PASS+1))
else
  echo "❌ FAIL: $COURSE_RESPONSE"
  FAIL=$((FAIL+1))
fi

# Test 3: Enroll Student in Course
echo ""
echo "Test 3: Enroll Student in Course (Student $STUDENT_ID, Course $COURSE_ID)"
ENROLL_RESPONSE=$(curl -s -X POST $BASE_URL/api/enrollments \
  -H "Content-Type: application/json" \
  -d "{\"studentId\":$STUDENT_ID,\"courseId\":$COURSE_ID}")
if echo "$ENROLL_RESPONSE" | grep -q "ENROLLED"; then
  echo "✅ PASS"
  PASS=$((PASS+1))
else
  echo "❌ FAIL: $ENROLL_RESPONSE"
  FAIL=$((FAIL+1))
fi

# Test 4: Get Student Enrollments
echo ""
echo "Test 4: Get Student Enrollments"
RESULT=$(curl -s $BASE_URL/api/enrollments/student/$STUDENT_ID)
if echo "$RESULT" | grep -q "courseId"; then
  echo "✅ PASS"
  PASS=$((PASS+1))
else
  echo "❌ FAIL: $RESULT"
  FAIL=$((FAIL+1))
fi

# Test 5: Get All Students
echo ""
echo "Test 5: Get All Students"
RESULT=$(curl -s $BASE_URL/api/students)
if echo "$RESULT" | grep -q "STU"; then
  echo "✅ PASS"
  PASS=$((PASS+1))
else
  echo "❌ FAIL: $RESULT"
  FAIL=$((FAIL+1))
fi

# Test 6: Get All Courses
echo ""
echo "Test 6: Get All Courses"
RESULT=$(curl -s $BASE_URL/api/courses)
if echo "$RESULT" | grep -q "CS"; then
  echo "✅ PASS"
  PASS=$((PASS+1))
else
  echo "❌ FAIL: $RESULT"
  FAIL=$((FAIL+1))
fi

echo ""
echo "========================================="
echo "  Results: $PASS passed, $FAIL failed"
echo "========================================="