FROM node:lts-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json (if available) into the container
COPY package*.json ./

# Install project dependencies
RUN npm install http-server --save-dev

# Copy the rest of the project files into the container
COPY . .

# Build the app for production with minification
RUN npm run build

# Expose the port that http-server will serve on
EXPOSE 8081

# Use http-server to serve the app
CMD ["npx","http-server", "dist", "-p", "8081"]
