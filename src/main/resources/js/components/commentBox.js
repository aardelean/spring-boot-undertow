var fetchCommentsUrl= 'http://localhost:8080/api/comments';
var data = [
  {author: "Pete Hunt", text: "This is one comment"},
  {author: "Jordan Walke", text: "This is *another* hashtag"}
];
var CommentBox = React.createClass({
  getInitialState: function() {
    return {data: []};
  },
  componentDidMount: function() {
      $.ajax({
        url: this.props.url,
        dataType: 'json',
        cache: false,
        success: function(data) {
          this.setState({data: data.content});
        }.bind(this),
        error: function(xhr, status, err) {
          console.error(this.props.url, status, err.toString());
        }.bind(this)
      });
    },
  render: function() {
    return (
    <div className="commentBox">
      <h1>Comments</h1>
       <CommentList data={this.state.data}/>
       <CommentForm />
    </div>
    );
  }
});

var CommentList = React.createClass({
  render: function() {
    var comments = this.props.data.map(function(comment){
       return (<Comment author={comment.author}>{comment.text}</Comment>);
    });
    return (
      <div className="commentList">
        {comments}
      </div>
    );
  }
});

var CommentForm = React.createClass({
  render: function() {
    return (
      <div className="commentForm">
        Hello, world! I am a CommentForm.
      </div>
    );
  }
});
var Comment = React.createClass({
  render: function() {
    var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
    return (

      <div className="comment">
        <h2 className="commentAuthor">
          {this.props.author}
        </h2>
        <span dangerouslySetInnerHTML={{__html: rawMarkup}} />
      </div>
    );
  }
});


React.render(
  <CommentBox url={fetchCommentsUrl}/>,
  document.getElementById('content')
);